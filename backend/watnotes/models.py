"""This module defines the data models for Watnotes."""

from datetime import datetime

from sqlalchemy import (
    Column, DateTime, ForeignKey, Integer, LargeBinary, String
)

from watnotes.database import db


# Mapping from Note formats to bytes->str decoders for serialization.
INLINE_DECODER = {
    'text': lambda data: data.decode('utf-8')
}

# Mapping from Note formats to MIME types.
MIME_TYPES = {
    'text': 'text/plain',
    'png': 'image/png'
}


class User(db.Model):
    """A user of Watnotes."""

    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    def serialize(self):
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

    def serialize(self):
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

    user = db.relationship(
        'User', backref=db.backref('notebooks', lazy=True))
    course = db.relationship(
        'Course', backref=db.backref('notebooks', lazy=True))

    def serialize(self):
        return {
            'id': self.id,
            'user_id': self.user_id,
            'course_id': self.course_id
        }

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

    notebook = db.relationship(
        'Notebook', backref=db.backref('notes', lazy=True))

    def mime_type(self):
        """Return the MIME type of the note's data."""
        return MIME_TYPES[self.format]

    def serialize(self):
        result = {
            'id': self.id,
            'notebook_id': self.notebook_id,
            'index': self.index,
            'format': self.format
        }

        decode = INLINE_DECODER.get(self.format)
        if decode:
            result['data'] = decode(self.data)

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

    user = db.relationship('User', backref=db.backref('comments', lazy=True))
    note = db.relationship('Note', backref=db.backref('comments', lazy=True))

    def serialize(self):
        return {
            'id': self.id,
            'user_id': self.user_id,
            'note_id': self.note_id,
            'content': self.content
        }

    def __repr__(self):
        return "<Comment #{} u#{}>".format(self.id, self.user_id)
