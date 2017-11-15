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
