"""This script refreshes elasticsearch from the database."""

import sys

from watnotes.database import db, is_db_running
from watnotes.models import models
from watnotes.search import es_delete_all


def main():
    if not is_db_running():
        print("ERROR: database is not running")
        sys.exit(1)
    if not is_es_running():
        print("ERROR: elasticsearch is not running")
        sys.exit(1)

    es_delete_all()
    for m in models:
        print(" * Refreshing table '{}'".format(m.__tablename__))
        for o in m.query.all():
            print("   * Refreshing obj '{}'".format(o))
            o.put_to_es()


main()
