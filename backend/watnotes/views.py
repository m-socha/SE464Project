"""This module defines Flask views (routes) for the application."""

from watnotes import app


@app.route('/')
def index():
    """Homepage."""
    return "Hello World!"
