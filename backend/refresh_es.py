"""This script refreshes elasticsearch from the database."""

import sys

from watnotes.database import db, is_db_running
from watnotes.models import models
from watnotes.search import es_delete_all, is_es_running


def main():
    if not is_db_running():
        print("ERROR: database is not running")
        sys.exit(1)
    if not is_es_running():
        print("ERROR: elasticsearch is not running")
        sys.exit(1)

    for m in models:
        m.delete_all_from_es()
        print(" * Refreshing index '{}'".format(m.es_index()))
        for o in m.query.all():
            o.put_to_es()


main()
