"""This module defines searching functionality."""

from typing import Any, Dict, List

from elasticsearch import Elasticsearch


ALREADY_EXISTS = 400
INDEX = 'documents'
es = Elasticsearch()
es.indices.create(index=INDEX, ignore=ALREADY_EXISTS)


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
    for result in response['hits']['hits']:
        item = result['_source']
        item['type'] = result['_type']
        item['id'] = result['_id']
        items.append(item)
    return {'items': items}
