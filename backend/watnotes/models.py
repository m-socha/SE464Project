"""This module defines the data models for Watnotes."""

from sqlalchemy import (
    Column, DateTime, Float, ForeignKey, Integer, LargeBinary, String
)
from sqlalchemy.orm import joinedload, validates

from watnotes.database import db
from watnotes.errors import InvalidAttribute
from watnotes.formats import (
    is_valid_mime, mime_is_image, mime_is_text, mime_to_extension
)
from watnotes.image import process_image_data
from watnotes.search import (
    es_create_index, es_delete, es_delete_all, es_insert
)


# Double-precision floating-point type.
Double = Float(precision=53)


class BaseModel(db.Model):
    """Base class for all models."""

    __abstract__ = True

    created_at = Column(DateTime, default=db.func.now())
    updated_at = Column(DateTime, default=db.func.now(),
                        onupdate=db.func.now())

    @classmethod
    def relations(cls):
        """Return a mapping from names to relation objects.

        This maps singular names to relation objects that are on the "one" side
        of a one-to-many relationship. For example, if there is one User for
        each Comment, then the Comment relations method should map 'user' to
        that User relation object designating a unique user, and the User
        relations method should not mention Comment at all.
        """
        return {}

    @classmethod
    def preloaded(cls, preloads, abort=None):
        """Return a query for the model with preloads configured.

        If abort isn't none, calls it when one of the preloads is invalid for
        this model. Otherwise, silently ignores invalid preloads.
        """
        query = cls.query
        relations = cls.relations()
        for name in preloads:
            rel = relations.get(name)
            if rel is not None:
                query = query.options(joinedload(rel, innerjoin=True))
            elif abort is not None:
                abort(404, "Invalid preload name '{}' for {}".format(
                    name, cls.__name__))

        return query

    @classmethod
    def es_index(cls):
        """Return the index name for this model in Elasticsearch."""
        return cls.__tablename__

    @classmethod
    def create_es_index(cls):
        """Create the index in Elasticsearch for this model."""
        es_create_index(cls.es_index())

    @classmethod
    def delete_all_from_es(cls):
        """Delete all documents from the Elasticsearch index for this model."""
        es_delete_all(cls.es_index())

    def serialize(self, preloads=None):
        """Serialize the model to a dict."""
        raise NotImplementedError()

    def serialize_es(self):
        """Serialize the model to a dict to store in Elasticsearch."""
        raise NotImplementedError()

    def before_commit(self):
        """Hook that is run before insert and before update."""
        self.process()

    def after_commit(self):
        """Hook that is run after insert and after update."""
        self.put_to_es()

    def before_delete(self):
        """Hook that is run before delete."""

    def after_delete(self):
        """Hook that is run after delete."""
        self.delete_from_es()

    def put_to_es(self):
        """Insert or update the model in Elasticsearch."""
        doc = self.serialize_es()
        if doc:
            es_insert(self.es_index(), self.id, doc)

    def delete_from_es(self):
        """Insert or update the model in Elasticsearch."""
        es_delete(self.es_index(), self.id)

    def process(self):
        """Process the model before committing it in the database."""


@db.event.listens_for(BaseModel, ('before_insert'), propagate=True)
@db.event.listens_for(BaseModel, ('before_update'), propagate=True)
def before_commit(mapper, connect, target):
    target.before_commit()


@db.event.listens_for(BaseModel, ('after_insert'), propagate=True)
@db.event.listens_for(BaseModel, ('after_update'), propagate=True)
def after_commit(mapper, connect, target):
    target.after_commit()


@db.event.listens_for(BaseModel, ('before_delete'), propagate=True)
def before_delete(mapper, connect, target):
    target.before_delete()


@db.event.listens_for(BaseModel, ('after_delete'), propagate=True)
def after_delete(mapper, connect, target):
    target.after_delete()


class User(BaseModel):
    """A user of Watnotes."""

    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)

    @classmethod
    def es_fields(cls):
        return ['email', 'name']

    def serialize(self, preloads=None):
        return {
            'id': self.id,
            'email': self.email,
            'name': self.name
        }

    def serialize_es(self):
        return {
            'email': self.email,
            'name': self.name
        }

    def __repr__(self):
        return "<User #{} {}>".format(self.id, self.email)


class Course(BaseModel):
    """A course that a user takes."""

    __tablename__ = 'courses'

    id = Column(Integer, primary_key=True)
    code = Column(String, nullable=False, unique=True)
    title = Column(String, nullable=False)

    def serialize(self, preloads=None):
        return {
            'id': self.id,
            'code': self.code,
            'title': self.title
        }

    def serialize_es(self):
        return {
            'code': self.code,
            'title': self.title
        }

    def __repr__(self):
        return "<Course #{} {}>".format(self.id, self.code)


class Notebook(BaseModel):
    """A collection of notes made by a user for a course."""

    __tablename__ = 'notebooks'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey(User.id), nullable=False)
    course_id = Column(Integer, ForeignKey(Course.id), nullable=False)

    user = db.relationship('User', backref=db.backref('notebooks'))
    course = db.relationship('Course', backref=db.backref('notebooks'))

    @classmethod
    def relations(cls):
        return {'user': cls.user, 'course': cls.course}

    def serialize(self, preloads=None):
        result = {'id': self.id}
        for name in 'user', 'course':
            if preloads and name in preloads:
                result[name] = getattr(self, name).serialize(preloads)
            else:
                name_id = name + '_id'
                result[name_id] = getattr(self, name_id)

        return result

    def serialize_es(self):
        # TODO: why doesn't self.user, self.course work?
        user = User.query.get(self.user_id)
        course = Course.query.get(self.course_id)
        return {
            'name': user.name,
            'code': course.code,
            'title': course.title
        }

    def __repr__(self):
        return "<Notebook #{} u#{} c#{}>".format(
            self.id, self.user_id, self.course_id)


class Note(BaseModel):
    """A chunk of material within a notebook."""

    __tablename__ = 'notes'

    id = Column(Integer, primary_key=True)
    notebook_id = Column(Integer, ForeignKey(Notebook.id), nullable=False)
    index = Column(Double, nullable=False)
    format = Column(String, nullable=False)
    data = Column(LargeBinary, nullable=False)

    notebook = db.relationship('Notebook', backref=db.backref('notes'))

    # Mapping from Note formats to bytes->str encoders.
    ENCODERS = {
        'text/plain': lambda data: data.decode('utf-8')
    }

    # Mapping from Note formats to str->bytes decoders.
    DECODERS = {
        'text/plain': lambda string: string.encode('utf-8')
    }

    @classmethod
    def relations(cls):
        return {'notebook': cls.notebook}

    @validates('format', 'data')
    def validate_format(self, key, value):
        error = None
        if key == 'format':
            if not is_valid_mime(value):
                error = "Unrecognized MIME type"
        elif key == 'data':
            if isinstance(value, str):
                decode = self.DECODERS.get(self.format)
                if not decode:
                    error = "No decoder found"
                else:
                    try:
                        value = decode(value)
                    except ValueError as e:
                        error = "Failed to decode"
            elif not isinstance(value, bytes):
                error = "Expected bytes"

        if error is not None:
            raise InvalidAttribute(key, value, error)
        return value

    def process(self):
        if len(self.data) > 0 and mime_is_image(self.format):
            self.data, self.format = process_image_data(self.data, self.format)

    def get_data(self, mime_type):
        """Return the note's data and a filename for it."""
        if mime_type != self.format:
            return None

        filename = str(self.id)
        return self.data, filename

    def serialize(self, preloads=None):
        result = {
            'id': self.id,
            'index': self.index,
            'format': self.format,
            'extension': mime_to_extension(self.format)
        }

        if preloads and 'notebook' in preloads:
            result['notebook'] = self.notebook.serialize(preloads)
        else:
            result['notebook_id'] = self.notebook_id

        encode = self.ENCODERS.get(self.format)
        if encode:
            result['data'] = encode(self.data)

        return result

    def serialize_es(self):
        if mime_is_text(self.format) and self.format in self.ENCODERS:
            return {'data': self.ENCODERS[self.format](self.data)}
        return None

    def __repr__(self):
        return "<Note #{} nb#{} fmt=\"{}\">".format(
            self.id, self.notebook_id, self.format)


class Comment(BaseModel):
    """A comment made by a user on a notebook."""

    __tablename__ = 'comments'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey(User.id), nullable=False)
    note_id = Column(Integer, ForeignKey(Note.id), nullable=False)
    content = Column(String, nullable=False)

    user = db.relationship('User', backref=db.backref('comments'))
    note = db.relationship('Note', backref=db.backref('comments'))

    @classmethod
    def relations(cls):
        return {'user': cls.user, 'note': cls.note}

    def serialize(self, preloads=None):
        result = {
            'id': self.id,
            'content': self.content
        }
        for name in 'user', 'note':
            if preloads and name in preloads:
                result[name] = getattr(self, name).serialize(preloads)
            else:
                name_id = name + '_id'
                result[name_id] = getattr(self, name_id)

        return result

    def serialize_es(self):
        return {
            'name': self.user.name,
            'content': self.content
        }

    def __repr__(self):
        return "<Comment #{} u#{}>".format(self.id, self.user_id)


models = [User, Course, Notebook, Note, Comment]
for m in models:
    m.create_es_index()
