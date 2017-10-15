"""This module defines helper functions for CRUDL-style resources."""

from typing import Sequence, Type

from flask import abort, request

from watnotes.database import db


class paginate(model: Type[db.Model]) -> str:
    """List resource items in a paginated fashion."""
    return "hi"


def get(model: Type[db.Model], id: str) -> str:
    """Get an existing resource item."""
    try:
        id = int(id)
    except ValueError:
        abort(404)
    object = model.query.get(id)
    if object:
        return repr(object)
    abort(404)


def create(model: Type[db.Model], required: Sequence[str],
           permitted: Sequence[str]=None) -> str:
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
