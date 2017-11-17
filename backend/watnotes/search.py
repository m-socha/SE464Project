"""This module defines searching functionality."""

from itertools import islice
from typing import Any, Dict, List
import os
import re

from elasticsearch import Elasticsearch
import certifi

from watnotes import app


# Use a single type for Elasticsearch 6.
TYPE = 'default'


def get_es_header():
    """Get the ES header from the environment or configuration files."""
    bonsai = os.environ.get('BONSAI_URL')
    if bonsai:
        auth = re.search('https\:\/\/(.*)\@', bonsai).group(1).split(':')
        host = bonsai.replace('https://%s:%s@' % (auth[0], auth[1]), '')
        return [{
            'host': host,
            'port': 443,
            'use_ssl': True,
            'http_auth': (auth[0], auth[1])
        }]

    return [app.config['ES_CONNECTION']]


es = Elasticsearch(get_es_header())


def is_es_running():
    """Return true if the Elasticsearch connection is working."""
    return es.ping()


def es_create_index(index: str):
    """Create an index in Elasticsearch if it doesn't exist."""
    exists = 400
    es.indices.create(index=index, ignore=exists)


def es_delete_all(index: str):
    """Delete all documents in an Elasticsearch index."""
    es.indices.delete(index=index)
    es.indices.create(index=index)


def es_insert(index: str, id: int, body: Dict[str, Any]):
    """Insert a document into Elasticsearch."""
    es.index(index=index, doc_type=TYPE, id=id, body=body)


def es_delete(index: str, id: int):
    """Delete a document from Elasticsearch."""
    es.delete(index=index, doc_type=TYPE, id=id)


def es_search(query: str, limit: int) -> List[Dict[str, Any]]:
    """Search for documents in Elasticsearch."""
    body = {'query': {'query_string': {'query': query}}}
    response = es.search(body=body)
    items = []
    for result in islice(response['hits']['hits'], limit):
        item = result['_source']
        item['type'] = result['_index']
        item['id'] = result['_id']
        items.append(item)
    return {'items': items}
