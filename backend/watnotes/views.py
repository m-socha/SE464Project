"""This module defines the Flask views (routes) for the server."""

from watnotes import app
from watnotes.database import db
from watnotes.models import User

from flask import request


@app.route('/')
def index():
    """Homepage."""
    return "Hello World!"


@app.route('/user/<id>')
def get_user(id):
    return repr(User.query.get(int(id)))


@app.route('/user/create', methods=['POST'])
def create_user():
    json = request.get_json()
    user = User(email=json['email'], name=json['name'])
    db.session.add(user)
    db.session.commit()
    return 'OK'
