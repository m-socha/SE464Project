# Watnotes backend

The Watnotes backend implements the core logic and data management for the
Watnotes application. It provides APIs, used by the website and mobile app, for
accessing and manipulating users, notes, and other data. It is written in Python
and uses [Flask][] and [PostgreSQL][].

## Setup

### Depedencies

Run `./install_deps.sh` to install all dependencies.

If the script doesn't work, install the following packages manually:

- Python 3.6
- PostgreSQL 9.6

Then, install the Python dependencies:

```
$ pip3 install -r requirements.txt
```

### Database

Before you run the server, create the database using `db.sh`:

```
$ ./db.sh create
 * Deleting database directory database/
 * Creating a postgres database cluster in database/
 * Starting postgres (logs in postgres.log)
 * Note: config file instance/application.cfg already exists
 * Running ./init_db.py
 * Stopping postgres
```

If there are problems, try again with the `-v` flag for verbose output.

You can look around the database and run ad hoc queries using `./db.sh psql`. If
you want to set a password on your PostgreSQL user, you can do so in psql using
`\password`. Then, you'll have to put it in `instance/application.cfg` so that
the Flask app can connect to the database.

## Run

To run the server, use `run.sh` (with `-d` for debug mode):

```
$ ./run.sh
 * Starting postgres (logs in postgres.log)
 * Serving Flask app "watnotes"
 * Forcing debug mode off
 * Running on http://127.0.0.1:5000/ (Press CTRL+C to quit)
...
```

Note that `run.sh` automatically starts PostgreSQL.

## Style

Code must adhere to [PEP 8][]. If you `pip3 install pep8`, you can run `pep8` on
Python files to detect style violations.

When using docstrings, follow [PEP 257][].

Finally, use [PEP 484][] type hints wherever it helps readability.

[Flask]: http://flask.pocoo.org/
[PostgreSQL]: https://www.postgresql.org/
[PEP 8]: https://www.python.org/dev/peps/pep-0008/
[PEP 257]: https://www.python.org/dev/peps/pep-0257/
[PEP 484]: https://www.python.org/dev/peps/pep-0484/
