"""This module configures the SQLAlchemy database engine."""

from flask_sqlalchemy import SQLAlchemy

from watnotes import app


app.config['SQLALCHEMY_DATABASE_URI'] = \
    "{dialect}+{driver}://{user}:{password}@{host}/{dbname}" \
    .format(user=app.config['DB_USER'],
            password=app.config['DB_PASSWORD'],
            **app.config['DB_CONNECTION'])
db = SQLAlchemy(app)
