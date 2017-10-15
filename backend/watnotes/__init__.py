"""Package watnotes is a Flask backend for the Watnotes application."""

from flask import Flask
app = Flask(__name__, instance_relative_config=True)
app.config.from_object('watnotes.default_config')
app.config.from_pyfile('application.cfg', silent=True)

import watnotes.views
