import axios from 'axios';

import {get} from 'services/request';
import { REQUEST_NOTEBOOK, RECEIVE_NOTEBOOK } from 'constants/data';

function requestNotebook(notebookID) {
  return {
    type: REQUEST_NOTEBOOK,
    notebookID,
  };
}

function receiveNotebook(notebookID, json) {
  return {
    type: RECEIVE_NOTEBOOK,
    notebookID,
  };
}

export default function fetchNotebook(notebookID) {
  return (dispatch) => {
    dispatch(requestNotebook(notebookID));
    get(`/notebooks/${notebookID}/notes`, null, (response) => {
      dispatch(receiveNotebook(notebookID, response));
    })
  };
}
