"""This module defines the Flask views (routes) for the server."""

from watnotes import app
from watnotes.crud_helpers import create, get
from watnotes.database import db
from watnotes.models import *


@app.route('/')
def index():
    return "Hello World!"


@app.route('/user/<id>')
def get_user(id):
    return get(User, id)


@app.route('/user/create', methods=['POST'])
def create_user():
    return create(User, ['email', 'name'])


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
