# Watnotes backend

The Watnotes backend is written using Python and [Flask][]. It serves pages for
the Watnotes website, and provides endpoints used by the website and mobile app.

## Setup

First, install Python 3.6:

```
$ python3 --version
Python 3.6.2
```

On macOS, you can do with [Homebrew][] by running `brew install python3`.

Second, install the Python requirements:

```
$ pip3 install -r requirements.txt
```

## Run

To run the server, use `run.py`:

```
$ ./run.py
 * Running on http://127.0.0.1:5000/ (Press CTRL+C to quit)
...
```

## Style

Code must adhere to [PEP 8][]. If you `pip3 install pep8`, you can run `pep8` on
Python files to detect style violations.

When using docstrings, follow [PEP 257][].

Finally, use [PEP 484][] type hints as much as possible.

[Flask]: http://flask.pocoo.org/
[Hombebrew]: https://brew.sh/
[PEP 8]: https://www.python.org/dev/peps/pep-0008/
[PEP 257]: https://www.python.org/dev/peps/pep-0257/
[PEP 484]: https://www.python.org/dev/peps/pep-0484/
