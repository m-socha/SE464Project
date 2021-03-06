"""Package watnotes is a Flask backend for the Watnotes application."""

import warnings
from flask.exthook import ExtDeprecationWarning
warnings.simplefilter('ignore', ExtDeprecationWarning)

from flask import Flask
from flask_cors import CORS

app = Flask(__name__, instance_relative_config=True)
CORS(app)

app.config.from_object('watnotes.default_config')
app.config.from_pyfile('application.cfg', silent=True)

import watnotes.views
