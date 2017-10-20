import { RECEIVE_FEED } from '../constants/data';
import {createReducer} from './util';

function receiveFeed(feedState, action) {
  return Object.assign([], feedState, action.feed);
}

export default createReducer([], {
  RECEIVE_FEED: receiveFeed,
});