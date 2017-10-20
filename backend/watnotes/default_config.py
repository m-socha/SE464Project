"""This module defines the default configuration for Watnotes."""

DB_CONNECTION = {
    'dialect': 'postgresql',
    'driver': 'psycopg2',
    'dbname': 'watnotes',
    'host': 'localhost',
    'port': 5432
}

MAX_IMAGE_WIDTH = 2000
MAX_IMAGE_HEIGHT = 1800
