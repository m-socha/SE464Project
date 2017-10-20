"""This module defines helper functions for CRUD operations."""

from typing import Mapping, Sequence, Type

from flask import abort, jsonify, request

from watnotes.database import db


def get_int(name: str) -> int:
    """Get an integer from the query string."""
    value = request.args.get(name)
    if not value:
        return None
    try:
        return int(value)
    except ValueError:
        abort(404, "Query parameter '{}' is not an integer".format(name))


def get_json():
    """Get the request JSON, or abort 404 if it doesn't exist."""
    json = request.get_json()
    if not json:
        abort(404, "Expected JSON in request body")
    return json


def get_or_404(model: Type[db.Model], id: int):
    object = model.query.get(id)
    if not object:
        abort(404, "{} with ID {} does not exist".format(model.__name__, id))


def paginate(model: Type[db.Model], order: str, **provided) -> str:
    """List resource items in a paginated fashion."""
    results = model.query.filter_by(**provided).order_by(order)

    page = get_int('page')
    per_page = get_int('per_page')
    if page is not None and per_page is not None:
        results = results.paginate(page, per_page)
        return jsonify(
            page=results.page,
            total_pages=results.pages,
            total_results=results.total,
            items=[item.serialize() for item in results.items])

    # By default, return all items.
    return jsonify(items=[item.serialize() for item in results])


def create(model: Type[db.Model], required: Sequence[str],
           permitted: Sequence[str]=None, **provided) -> str:
    """Create a new resource item."""
    if request.args.get('form') == '1':
        return create_form(model, required, permitted, **provided)

    json = get_json()
    fields = {}
    for f in required:
        if f in json:
            fields[f] = json[f]
        else:
            abort(404, "Required JSON attribute '{}' not found".format(f))
    if permitted:
        for f in permitted:
            if f in json:
                fields[f] = json[f]
    fields.update(provided)

    object = model(**fields)
    db.session.add(object)
    db.session.commit()
    return jsonify(object.serialize())


def create_form(model: Type[db.Model], required: Sequence[str],
                permitted: Sequence[str]=None, **provided) -> str:
    """Create a new resource item from form data."""
    fields = {}
    for f in required:
        if f in request.form:
            fields[f] = request.form[f]
        elif f in request.files:
            fields[f] = request.files[f].read()
    if permitted:
        for f in permitted:
            if f in request.form:
                fields[f] = request.form[f]
            elif f in request.files:
                fields[f] = request.files[f].read()
    fields.update(provided)

    object = model(**fields)
    db.session.add(object)
    db.session.commit()
    return jsonify(object.serialize())


def get(model: Type[db.Model], id: int) -> str:
    """Get an existing resource item."""
    return jsonify(get_or_404(model, id).serialize())


def update(model: Type[db.Model], id: int, permitted: Sequence[str]) -> str:
    """Update an existing resource item."""
    object = get_or_404(model, id)
    for k, v in get_json():
        if k in permitted:
            object[k] = v
    db.session.commit()
    return 'OK'


def delete(model: Type[db.Model], id: int) -> str:
    """Delete a resource item."""
    object = get_or_404(model, id)
    db.session.delete(object)
    db.session.commit()
    return 'OK'
