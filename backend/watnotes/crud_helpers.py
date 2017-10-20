"""This module defines helper functions for CRUD operations."""

from typing import Any, Mapping, Sequence, Type

from flask import abort, jsonify, make_response, request
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import joinedload

from watnotes.database import db
from watnotes.errors import InvalidAttribute
from watnotes.formats import extension_to_mime


def get_int(name: str) -> int:
    """Get an integer from the query string."""
    value = request.args.get(name)
    if not value:
        return None
    try:
        return int(value)
    except ValueError:
        abort(404, "Query parameter '{}' is not an integer".format(name))


def is_form() -> bool:
    """Return true if the client specified the input using form data."""
    return request.args.get('form') == '1'


def is_attachment() -> bool:
    """Return true if the client requested to download as an attachment."""
    return request.args.get('dl') == '1'


def get_preloads():
    """Return the list of preloads specified in the query parameters."""
    names = request.args.get('load')
    if names is None:
        return []
    return names.split(',')


def preloaded(model: Type[db.Model], preloads=None):
    """Return a query for the model with preloads configured."""
    if not preloads:
        preloads = get_preloads()

    query = model.query
    relations = model.relations()
    for name in preloads:
        rel = relations.get(name)
        if rel is None:
            abort(404, "Invalid preload name '{}' for {}".format(
                name, model.__name__))
        query = query.options(joinedload(rel, innerjoin=True))

    return query


def get_json():
    """Get the request JSON, or abort 404 if it doesn't exist."""
    json = request.get_json()
    if not json:
        abort(404, "Expected JSON in request body")
    return json


def get_fields(model_name: str,
               required: Sequence[str]=None,
               permitted: Sequence[str]=None,
               provided: Mapping[str, Any]=None) -> str:
    """Return input fields for a request."""
    required = required or []
    permitted = permitted or []
    provided = provided or {}

    fields = {}

    def form_or_file(field):
        if field in request.form:
            return request.form[field]
        if field in request.files:
            return request.files[field].read()
        return None

    if is_form():
        lookup = form_or_file
    else:
        json = get_json()
        lookup = lambda f: json.get(f)

    for f in required:
        value = lookup(f)
        if value is None:
            abort(404, "{} missing required attribute {}".format(model_name, f))
        else:
            fields[f] = value

    for f in permitted:
        value = lookup(f)
        if value is not None:
            fields[f] = value

    fields.update(provided)
    return fields


def get_or_404(model: Type[db.Model], id: int):
    """Get a model object by ID, and 404 if it doesn't exist."""
    object = preloaded(model).get(id)
    if not object:
        abort(404, "{} with ID {} does not exist".format(model.__name__, id))
    return object


def paginate(model: Type[db.Model], order: str, **provided) -> str:
    """List resource items in a paginated fashion."""
    preloads = get_preloads()
    results = preloaded(model, preloads).filter_by(**provided).order_by(order)

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
    return jsonify(items=[item.serialize(preloads) for item in results])


def create(model: Type[db.Model], required: Sequence[str],
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


def get(model: Type[db.Model], id: int) -> str:
    """Get an existing resource item."""
    return jsonify(get_or_404(model, id).serialize(get_preloads()))


def download(model: Type[db.Model], id: int, extension: str):
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


def update(model: Type[db.Model], id: int, permitted: Sequence[str]) -> str:
    """Update an existing resource item."""
    fields = get_fields(model.__name__, permitted=permitted)
    object = get_or_404(model, id)
    try:
        for k, v in fields:
            object[k] = v
        db.session.commit()
    except InvalidAttribute as e:
        abort(404, e)
    return 'OK'


def delete(model: Type[db.Model], id: int) -> str:
    """Delete a resource item."""
    object = get_or_404(model, id)
    db.session.delete(object)
    db.session.commit()
    return 'OK'
