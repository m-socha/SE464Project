import {get} from 'services/request';
import { REQUEST_FEED, RECEIVE_FEED } from 'constants/data';

function requestFeed() {
  return {
    type: REQUEST_FEED,
    source: 'feed'
  };
}

function receiveFeed(data) {
  return {
    type: RECEIVE_FEED,
    feed: data.items,
    source: 'feed'
  };
}

export function fetchFeed(userID, page) {
  return (dispatch) => {
    dispatch(requestFeed());
    get(`/users/${userID}/notebooks`, ({page, per_page: 20, load: 'user,course'}), (data, err) => {
      if (err) {
        console.log(`Error while fetching feed for ${userID} - ${err}`);
      }

      dispatch(receiveFeed(data));
    });
  };
}
