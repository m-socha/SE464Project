"""This module configures the SQLAlchemy database engine."""

from flask_sqlalchemy import SQLAlchemy

from watnotes import app


def get_database_uri(**params):
    uri = "{dialect}+{driver}://{user}:{password}@{host}/{dbname}"
    return uri.format(**params)


app.config['SQLALCHEMY_DATABASE_URI'] = get_database_uri(
    user=app.config['DB_USER'],
    password=app.config['DB_PASSWORD'],
    **app.config['DB_CONNECTION'])
db = SQLAlchemy(app)
