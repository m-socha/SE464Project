"""This module defines the data models for Watnotes."""

from sqlalchemy import Column, Integer, String

from watnotes.database import db


class User(db.Model):
    """A user of Watnotes."""

    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)

    def __repr__(self):
        return "<User #{} {}>".format(self.id, self.email)
