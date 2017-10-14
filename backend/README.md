# Watnotes backend

The Watnotes backend serves pages for the Watnotes website, and provides
endpoints used by the website and mobile app. It is written in Python, and it
uses [Flask][] as its web framework and [PostgreSQL][] for its database.

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
$ database/db.sh create
 * Deleting database directory data/
 * Creating a postgres database cluster in data/
 * Starting postgres (logs in postgres.log)
 * Running init.sql
 * Stopping postgres
```

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
