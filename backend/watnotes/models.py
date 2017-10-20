"""This module defines the data models for Watnotes."""

from datetime import datetime

from sqlalchemy import (
    Column, DateTime, ForeignKey, Integer, LargeBinary, String
)
from sqlalchemy.orm import validates

from watnotes.database import db
from watnotes.errors import InvalidAttribute
from watnotes.formats import is_valid_mime, mime_to_extension


class User(db.Model):
    """A user of Watnotes."""

    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    @classmethod
    def relations(cls):
        return {}

    def serialize(self, preloads=None):
        return {
            'id': self.id,
            'email': self.email,
            'name': self.name
        }

    def __repr__(self):
        return "<User #{} {}>".format(self.id, self.email)


class Course(db.Model):
    """A course that a user takes."""

    __tablename__ = 'courses'

    id = Column(Integer, primary_key=True)
    code = Column(String, nullable=False, unique=True)
    title = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    @classmethod
    def relations(cls):
        return {}

    def serialize(self, preloads=None):
        return {
            'id': self.id,
            'code': self.code,
            'title': self.title
        }

    def __repr__(self):
        return "<Course #{} {}>".format(self.id, self.code)


class Notebook(db.Model):
    """A collection of notes made by a user for a course."""

    __tablename__ = 'notebooks'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey(User.id), nullable=False)
    course_id = Column(Integer, ForeignKey(Course.id), nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

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

    def __repr__(self):
        return "<Notebook #{} u#{} c#{}>".format(
            self.id, self.user_id, self.course_id)


class Note(db.Model):
    """A chunk of material within a notebook."""

    __tablename__ = 'notes'

    id = Column(Integer, primary_key=True)
    notebook_id = Column(Integer, ForeignKey(Notebook.id), nullable=False)
    index = Column(Integer, nullable=False, unique=True)
    format = Column(String, nullable=False)
    data = Column(LargeBinary, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    notebook = db.relationship('Notebook', backref=db.backref('notes'))

    # Mapping from Note formats to bytes->str encoders.
    ENCODERS = {
        'text/plain': lambda data: data.decode('utf-8')
    }

    # Mapping from Note formats to str->bytes decoders.
    DECODERS = {
        'text/plain': lambda string: string.encode('utf-8')
    }

    def __init__(self, **kwargs):
        """Create a new Note, optionally with inline data."""
        format = kwargs['format']
        data = kwargs.get('data')
        if data is None:
            data = b''
        elif isinstance(data, str) and is_valid_mime(format):
            decode = self.DECODERS.get(format)
            if not decode:
                raise Exception("Passed inline data for format without decoder")
            data = decode(data)

        kwargs['data'] = data
        super().__init__(**kwargs)

    @classmethod
    def relations(cls):
        return {'notebook': cls.notebook}

    @validates('format')
    def validate_format(self, key, format):
        if not is_valid_mime(format):
            raise InvalidAttribute(key, format)
        return format

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

    def __repr__(self):
        return "<Note #{} u#{} nb#{}>".format(
            self.id, self.user_id, self.notebook_id)


class Comment(db.Model):
    """A comment made by a user on a notebook."""

    __tablename__ = 'comments'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey(User.id), nullable=False)
    note_id = Column(Integer, ForeignKey(Note.id), nullable=False)
    content = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

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

    def __repr__(self):
        return "<Comment #{} u#{}>".format(self.id, self.user_id)
