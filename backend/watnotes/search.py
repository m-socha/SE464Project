"""This module defines searching functionality."""

from itertools import islice
from math import ceil
from typing import Any, Dict, List
import os
import re

from elasticsearch import Elasticsearch
import certifi

from watnotes import app, crud_helpers, models
from watnotes.arguments import get_preloads, is_flat


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


def es_search(query: str, page: int, per_page: int, indices: List[str]=None):
    """Search for documents in Elasticsearch.

    Returns serialized models from the database based on Elasticsearch results.
    """
    assert page >= 1
    assert per_page >= 1

    body = {'query': {'query_string': {'query': query}}}
    response = es.search(index=indices, body=body, from_=page-1, size=per_page)

    index_id_score = {}
    for hit in response['hits']['hits']:
        index = hit['_index']
        if index not in index_id_score:
            index_id_score[index] = {}
        index_id_score[index][int(hit['_id'])] = hit['_score']

    flat = is_flat()
    preloads = get_preloads()
    items = [] if flat else {}
    for m in models.models:
        index = m.es_index()
        if index not in index_id_score:
            if not flat:
                items[index] = []
            continue
        id_score = index_id_score[index]
        ids = id_score.keys()
        objs = m.preloaded(preloads).filter(m.id.in_(ids)).all()
        objs.sort(key=lambda o: id_score[o.id], reverse=True)
        if flat:
            serial = [o.serialize(preloads) for o in objs]
            for s in serial:
                s['type'] = index
            items.extend(serial)
        else:
            items[index] = [o.serialize(preloads) for o in objs]

    total = response['hits']['total']
    return dict(
        page=page,
        total_pages=ceil(total / per_page),
        total_results=total,
        items=items)
