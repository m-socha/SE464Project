"""This module defines searching functionality."""

from itertools import islice
from typing import Any, Dict, List
import os
import re

from elasticsearch import Elasticsearch

from watnotes import app


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


ALREADY_EXISTS = 400
INDEX = 'documents'
es = Elasticsearch(get_es_header())
es.indices.create(index=INDEX, ignore=ALREADY_EXISTS)


def is_es_running():
    """Return true if the elasticsearch connection is working."""
    return es.ping()


def es_insert(doc_type: str, id: int, body: Dict[str, Any]):
    """Insert a document into elasticsearch."""
    es.index(index=INDEX, doc_type=doc_type, id=id, body=body)


def es_delete(doc_type: str, id: int):
    """Delete a document from elasticsearch."""
    es.delete(index=INDEX, doc_type=doc_type, id=id)


def es_delete_all():
    """Delete all documents in elasticsearch."""
    es.indices.delete(index=INDEX)
    es.indices.create(index=INDEX)


def es_search(query: str, limit: int) -> List[Dict[str, Any]]:
    """Search for documents in elasticsearch."""
    body = {'query': {'query_string': {'query': query}}}
    response = es.search(index=INDEX, body=body)
    items = []
    for result in islice(response['hits']['hits'], limit):
        item = result['_source']
        item['type'] = result['_type']
        item['id'] = result['_id']
        items.append(item)
    return {'items': items}
