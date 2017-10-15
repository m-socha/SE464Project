"""This module defines the Flask views (routes) for the server."""

from watnotes import app
from watnotes.crud_helpers import create, delete, get, paginate, update
from watnotes.database import db
from watnotes.models import *

from flask import request


@app.route('/')
def index():
    return "Hello World!"


@app.route('/users', methods=['GET', 'POST'])
def users():
    if request.method == 'GET':
        return paginate(User, 'created_at')
    elif request.method == 'POST':
        return create(User, ['email', 'name'])


@app.route('/users/<int:id>', methods=['GET', 'PUT', 'DELETE'])
def users_id(id):
    if request.method == 'GET':
        return get(User, id)
    elif request.method == 'PUT':
        return update(User, id, ['email', 'name'])
    elif request.method == 'DELETE':
        return delete(User, id)


@app.route('/courses', methods=['GET', 'POST'])
def courses():
    if request.method == 'GET':
        return paginate(Course, 'code')
    elif request.method == 'POST':
        return create(Course, ['code', 'title'])


@app.route('/courses/<int:id>', methods=['GET', 'PUT', 'DELETE'])
def courses_id(id):
    if request.method == 'GET':
        return get(Course, id)
    elif request.method == 'PUT':
        return update(Course, id, ['code', 'title'])
    elif request.method == 'DELETE':
        return delete(Course, id)


@app.route('/users/<int:user_id>/notebooks', methods=['GET', 'POST'])
def notebooks(user_id):
    if request.method == 'GET':
        return paginate(Notebook, 'created_at', user_id=user_id)
    elif request.method == 'POST':
        return create(Notebook, ['course_id'], user_id=user_id)


@app.route('/notebooks/<int:id>', methods=['GET', 'PUT', 'DELETE'])
def notebooks_id(id):
    if request.method == 'GET':
        return get(Notebook, id)
    elif request.method == 'PUT':
        return update(Notebook, id, [])
    elif request.method == 'DELETE':
        return delete(Notebook, id)


@app.route('/notebooks/<int:notebook_id>/notes', methods=['GET', 'POST'])
def notes(notebook_id):
    if request.method == 'GET':
        return paginate(Note, 'index', notebook_id=notebook_id)
    elif request.method == 'POST':
        return create(Note, ['index', 'format', 'data'],
                      notebook_id=notebook_id)


@app.route('/notes/<int:id>', methods=['GET', 'PUT', 'DELETE'])
def notes_id(id):
    if request.method == 'GET':
        return get(Note, id)
    elif request.method == 'PUT':
        return update(Note, id, ['index', 'data'])
    elif request.method == 'DELETE':
        return delete(Note, id)


@app.route('/notes/<int:note_id>/comments', methods=['GET', 'POST'])
def comments():
    if request.method == 'GET':
        return paginate(Comment, 'created_at', note_id=note_id)
    elif request.method == 'POST':
        return create(Note, ['user_id', 'content'], note_id=note_id)


@app.route('/comments/<int:id>', methods=['GET', 'PUT', 'DELETE'])
def comments_id(id):
    if request.method == 'GET':
        return get(Comment, id)
    elif request.method == 'PUT':
        return update(Comment, id, ['content'])
    elif request.method == 'DELETE':
        return delete(Comment, id)
