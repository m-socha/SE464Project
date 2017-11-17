"""This script initializes the Watnotes database."""

import sys

from watnotes.database import db, is_db_running
from watnotes.search import is_es_running
import watnotes.models


def main():
    if not is_db_running():
        print("ERROR: database is not running")
        sys.exit(1)
    if not is_es_running():
        print("ERROR: elasticsearch is not running")
        sys.exit(1)

    db.create_all()


main()
