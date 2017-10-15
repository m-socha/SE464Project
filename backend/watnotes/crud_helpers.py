"""This module defines helper functions for CRUD operations."""

from typing import Sequence, Type

from flask import abort, request

from watnotes.database import db


def paginate(model: Type[db.Model], **provided) -> str:
    """List resource items in a paginated fashion."""
    return "Not implemented yet"


def create(model: Type[db.Model], required: Sequence[str],
           permitted: Sequence[str]=None, **provided) -> str:
    """Create a new resource item."""
    json = request.get_json()
    fields = {}
    for f in required:
        if f in json:
            fields[f] = json[f]
        else:
            pass
    if permitted:
        for f in permitted:
            if f in json:
                fields[f] = json[f]

    object = model(**fields)
    db.session.add(object)
    db.session.commit()
    return 'OK'


def get(model: Type[db.Model], id: int) -> str:
    """Get an existing resource item."""
    object = model.query.get(id)
    if object:
        return repr(object)
    abort(404)


def update(model: Type[db.Model], id: int) -> str:
    return "Not implemented yet"


def delete(model: Type[db.Model], id: int) -> str:
    return "Not implemented yet"
