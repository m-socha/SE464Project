"""This module defines the Flask views (routes) for the server."""

from watnotes import app
from watnotes.database import db_session


@app.route('/')
def index():
    """Homepage."""
    return "Hello World!"


@app.teardown_appcontext
def shutdown_session(exception=None):
    db_session.remove()
