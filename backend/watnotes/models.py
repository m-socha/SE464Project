"""This module defines the data models for Watnotes."""

from datetime import datetime

from sqlalchemy import (
    Column, DateTime, ForeignKey, Integer, LargeBinary, String
)

from watnotes.database import db


class User(db.Model):
    """A user of Watnotes."""

    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    def __repr__(self):
        return "<User #{} {}>".format(self.id, self.email)


class Course(db.Model):
    """A course that a user takes."""

    __tablename__ = 'courses'

    id = Column(Integer, primary_key=True)
    code = Column(String, nullable=False, unique=True)
    title = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

    def __repr__(self):
        return "<Course #{} {}>".format(self.id, self.code)


class Notebook(db.Model):
    """A collection of notes made by a user for a course."""

    __tablename__ = 'notebooks'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey(User.id), nullable=False)
    course_id = Column(Integer, ForeignKey(Course.id), nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

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

    def __repr__(self):
        return "<Comment #{} u#{}>".format(self.id, self.user_id)
