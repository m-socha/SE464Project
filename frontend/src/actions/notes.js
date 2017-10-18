import axios from 'axios';
import { REQUEST_NOTEBOOK, RECEIVE_NOTEBOOK } from '../constants/data';

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
    axios.get(`/notebooks/${notebookID}/notes`)
      .then((response) => {
        dispatch(receiveNotebook(notebookID, response));
      });
    // .catch(function (error) { // TODO: Add error handling
    //   console.log(error);
    // });
  };
}
