"""This module defines helper functions for CRUD operations."""

from typing import Sequence, Type

from flask import abort, jsonify, make_response, request
from sqlalchemy.exc import IntegrityError

from watnotes.arguments import (
    get_fields, get_int, get_preloads, is_attachment, is_form
)
from watnotes.database import db
from watnotes.errors import InvalidAttribute
from watnotes.formats import extension_to_mime
from watnotes.models import BaseModel


def get_or_404(model: Type[BaseModel], id: int):
    """Get a model object by ID, and 404 if it doesn't exist."""
    object = model.preloaded(get_preloads(), abort).get(id)
    if not object:
        abort(404, "{} with ID {} does not exist".format(model.__name__, id))
    return object


def paginate(model: Type[BaseModel], order: str, **provided) -> str:
    """List resource items in a paginated fashion."""
    preloads = get_preloads()
    results = model.preloaded(preloads, abort) \
        .filter_by(**provided).order_by(order)

    page = get_int('page')
    per_page = get_int('per_page')
    if page is not None and per_page is not None:
        results = results.paginate(page, per_page)
        return jsonify(
            page=results.page,
            total_pages=results.pages,
            total_results=results.total,
            items=[item.serialize(preloads) for item in results.items])

    # By default, return all items.
    items = [item.serialize(preloads) for item in results]
    return jsonify(
        page=1,
        total_pages=1,
        total_results=len(items),
        items=items)


def create(model: Type[BaseModel], required: Sequence[str],
           permitted: Sequence[str]=None, **provided) -> str:
    """Create a new resource item."""
    fields = get_fields(model.__name__, required, permitted, provided)
    try:
        object = model(**fields)
        db.session.add(object)
        db.session.commit()
    except IntegrityError as e:
        abort(404, "Integrity error: {}".format(e))
    except InvalidAttribute as e:
        abort(404, e)
    return jsonify(object.serialize(get_preloads()))


def get(model: Type[BaseModel], id: int) -> str:
    """Get an existing resource item."""
    return jsonify(get_or_404(model, id).serialize(get_preloads()))


def download(model: Type[BaseModel], id: int, extension: str):
    """Download a resource."""
    mime_type = extension_to_mime(extension)
    if mime_type is None:
        abort(404, "Unrecognized extension '{}'".format(extension))

    object = get_or_404(model, id)
    result = object.get_data(mime_type)
    if result is None:
        abort(404, "Cannot download {} with ID {} as '{}'".format(
            model.__name__, id, extension))

    data, filename = result
    response = make_response(data)
    response.headers['Content-Type'] = mime_type
    if is_attachment():
        disposition = "attachment; filename={}.{}".format(filename, extension)
        response.headers['Content-Disposition'] = disposition
    return response


def update(model: Type[BaseModel], id: int, permitted: Sequence[str]) -> str:
    """Update an existing resource item."""
    fields = get_fields(model.__name__, permitted=permitted)
    object = get_or_404(model, id)
    try:
        for k, v in fields.items():
            setattr(object, k, v)
        db.session.commit()
    except InvalidAttribute as e:
        abort(404, e)
    return 'OK'


def delete(model: Type[BaseModel], id: int) -> str:
    """Delete a resource item."""
    object = get_or_404(model, id)
    db.session.delete(object)
    db.session.commit()
    return 'OK'
