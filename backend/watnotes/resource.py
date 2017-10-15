"""This module defines a Flask view for CRUDL resources."""

from typing import Sequence, Type

from flask import Flask, abort, request

from watnotes.database import db


class Resource:
    """A REST-ful resource that supports CRUDL operations."""

    def __init__(self, name: str, model: Type[db.Model],
                 required: Sequence[str], allowed: Sequence[str],
                 parent: Resource=None):
        self.name = name
        self.model = model
        self.required = required
        self.allowed = allowed

    def list(self):
        return "Not implemented yet"

    def create(self):
        pass

    def get(self, id):
        object = self.model.query.get(id)
        if object:
            return repr(object)
        abort(404)

    def update(self, id):
        return "Not implemented yet"

    def delete(self, id):
        return "Not implemented yet"


class ResourceView(View):
    """Flask view for a Resource."""

    def __init__(self, resource: Resource):
        self.resource = resource

    def dispatch_request(self, id=None):
        if id is None:
            if request.method == 'GET':
                return self.list()
            elif request.method == 'POST':
                return self.create()
        else:
            try:
                id = int(id)
            except ValueError:
                abort(404)
            if request.method == 'GET':
                return self.get(id)
            elif request.method == 'PUT':
                return self.update(id)
            elif request.method == 'DELETE':
                return self.delete(id)

        raise Exception("Bad method: {}".format(request.method))


def register_resource(app: Flask, resource: Resource, prefix: str='/'):
    if resource.parent:
    else:
        app.add_url_rule("{}/", view_func=Resource.as_view(name, ))
