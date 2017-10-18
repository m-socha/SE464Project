import axios from 'axios';
import { REQUEST_NOTEBOOKS, RECEIVE_NOTEBOOKS } from '../constants/data';

function requestNotebooks(userID) {
  return {
    type: REQUEST_NOTEBOOKS,
    userID,
  };
}

function receiveNotebooks(userID, json) {
  return {
    type: RECEIVE_NOTEBOOKS,
    userID,
    notebooks: json.data.items,
    receivedAt: Date.now(),
  };
}

export default function fetchNotebooks(userID) {
  return (dispatch) => {
    dispatch(requestNotebooks(userID));
    axios.get(`/api/users/${userID}/notebooks`)
      .then((response) => {
        dispatch(receiveNotebooks(userID, response));
      });
    // .catch(function (error) {
    //   console.log(error);
    // });
  };
}
