import axios from 'axios';

import { REQUEST_NOTEBOOK, RECEIVE_NOTEBOOK } from '../constants/data';

function requestNotebook() {
  return {
    type: REQUEST_NOTEBOOK,
  }
}

function receiveNotebook(notebookID, notebook) {
  return {
    type: RECEIVE_NOTEBOOK,
    source: 'notebook',
    notebookID,
    notebook
  }
}

export function fetchNotebook(userID, notebookID) {
  return (dispatch) => {
    dispatch(requestNotebook());
    axios.get(`/users/${userID}/notebooks/${notebookID}/notes`)
      .then((response) => {
        dispatch(receiveNotebook(notebookID, response));
      });
  };
}
