"""This module defines the Flask views (routes) for the server."""

from watnotes import app
from watnotes.crud_helpers import create, get
from watnotes.database import db
from watnotes.models import *

users = Resource('users', User, ['email', 'name'])
courses = Resource('courses', Course, ['code', 'title'])
notebooks = Resource('notebooks', Notebook, ['user_id', 'course_id'])
notes = Resource('')

@app.route('/')
def index():
    return "Hello World!"


@app.route('/users', methods=['GET', 'POST'])
def users():
    if request.method == 'GET':
        return paginate(User)
    elif request.method == 'POST':
        return create(User, ['email', 'name'])


@app.route('/users/<id>', methods=['GET', 'PUT', 'DELETE'])
def get_user(id):
    if request.method == 'GET':
        return get(User, id)
    elif request.method == 'PUT':
        return put(User, id)
    elif request.method == 'DELETE':
        return delete(User, id)


@app.route('/course/<id>')
def get_course(id):
    return get(Course, id)


@app.route('/course/create', methods=['POST'])
def create_course(id):
    return create(Course, ['code', 'title'])


@app.route('/notebook/<id>')
def get_notebook(id):
    return get(Notebook, id)


@app.route('/notebook/create', methods=['POST'])
def create_notebook():
    return create(Notebook, ['user_id', 'course_id'])


@app.route('/note/<id>')
def get_note(id):
    return get(Note, id)


@app.route('/notebook/<id>/note/create', methods=['POST'])
def create_note():
    return create(Note, ['notebook_id', 'index', 'format', 'data'])


@app.route('/comment/<id>')
def get_comment(id):
    return get(Comment, id)


@app.route('/notebook/<id>/comment/create', methods=['POST'])
def create_comment():
    return create(Comment, ['user_id', 'note_id', 'content'])


# @app.route('/notebook/<id>/notes')
# def get_note_list
