#!/usr/bin/env python3

"""This script initializes the Watnotes database."""

from watnotes.database import db
import watnotes.models

db.create_all()
