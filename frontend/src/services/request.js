import axios from 'axios';

export function get(url, params, callback) {
  axios
    .get(`http://watnotes.herokuapp.com${url}`, {
      params
    })
    .then(response => {
      // TODO: check status and stuff.
      callback(response.data);
    })
    .catch(err => {
      callback(null, err);
    });
}