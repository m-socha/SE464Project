import axios from 'axios';

import {get} from 'services/request';
import { REQUEST_NOTEBOOK, RECEIVE_NOTEBOOK } from 'constants/data';

function requestNotebook(notebookID) {
  return {
    type: REQUEST_NOTEBOOK,
    notebookID,
    source: 'notebook',
  };
}

function receiveNotebook(notebookID, json) {
  return {
    type: RECEIVE_NOTEBOOK,
    notebookID,
    notebook: {
      notes: json.items,
      page: json.page,
      total_pages: json.total_pages,
      total_results: json.total_results,
    },
    source: 'notebook',
  };
}

export function fetchNotebook(notebookID) {
  return (dispatch) => {
    dispatch(requestNotebook(notebookID));
    get(`/notebooks/${notebookID}/notes`, { page: 1, per_page: 20 }, (response) => {
      dispatch(receiveNotebook(notebookID, response));
    });
  };
}
