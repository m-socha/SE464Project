import {get} from 'services/request';
import {REQUEST_SEARCH} from 'constants/data';


export function fetchSearchResults(query, cb) {
  get(`/search`, ({q: query, flat: 1}), (data, err) => {
    if (err) {
      console.log(`Error while fetching search lookaheads for ${query} - ${err}`);
    }

    if (data && data.items) {
      return cb(data.items)
    }
    return cb();
  });
}