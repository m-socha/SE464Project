"""This script refreshes elasticsearch from the database."""

from watnotes.database import db, is_db_running
from watnotes.models import models
from watnotes.search import es_delete_all


def main():
    if is_db_running():
        es_delete_all()
        for m in models:
            print(" * Refreshing table '{}'".format(m.__tablename__))
            for o in m.query.all():
                print("   * Refreshing obj '{}'".format(o))
                o.put_to_es()
    else:
        print("ERROR: database is not running")


main()
