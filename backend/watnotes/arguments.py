"""This module defines helper functions for extracting request arguments."""

from typing import Any, Dict, Mapping, Sequence

from flask import abort, request


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


def get_json():
    """Get the request JSON, or abort 404 if it doesn't exist."""
    json = request.get_json()
    if not json:
        abort(404, "Expected JSON in request body")
    return json


def get_fields(model_name: str,
               required: Sequence[str]=None,
               permitted: Sequence[str]=None,
               provided: Mapping[str, Any]=None) -> Dict[str, Any]:
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
            abort(404, "{} missing required attribute {}".format(
                model_name, f))
        else:
            fields[f] = value

    for f in permitted:
        value = lookup(f)
        if value is not None:
            fields[f] = value

    fields.update(provided)
    return fields
