"""Package watnotes is a Flask backend for the Watnotes application."""

from flask import Flask
app = Flask(__name__)

import watnotes.views
