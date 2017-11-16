import axios from 'axios';
import config from 'react-global-configuration';

export function get(url, params, callback) {
  axios
    .get(`${config.get('backend')}${url}`, {
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

export function post(url, body, callback) {
  axios.post(`${config.get('backend')}${url}`, body)
    .then((response) => {
      callback(response.data);
    })
    .catch((err) => {
      callback(null, err);
    });
}
