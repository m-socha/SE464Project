"""This module defines the Flask views (routes) for the server."""

from watnotes import app


@app.route('/')
def index():
    """Homepage."""
    return "Hello World!"
