"""This script initializes the Watnotes database."""

from watnotes.database import db, is_db_running
import watnotes.models


def main():
    if is_db_running():
        db.create_all()
    else:
        print("ERROR: database is not running")


main()
