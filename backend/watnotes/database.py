"""This module configures the SQLAlchemy database engine."""

from flask_sqlalchemy import SQLAlchemy

from watnotes import app

import os


def get_database_uri(**params):
    """Gets the database URI from the environment or configuration files."""
    uri = os.environ.get('DATABASE_URL')
    if uri:
        return uri
    uri = "{dialect}+{driver}://{user}:{password}@{host}/{dbname}"
    return uri.format(user=app.config['DB_USER'],
                      password=app.config['DB_PASSWORD'],
                      **app.config['DB_CONNECTION'])


app.config['SQLALCHEMY_DATABASE_URI'] = get_database_uri()
db = SQLAlchemy(app)
