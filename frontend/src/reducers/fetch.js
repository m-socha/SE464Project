import { REQUEST_NOTEBOOK, REQUEST_NOTEBOOKS } from '../constants/data';
import {createReducer} from './util';

function fetchingBegin(fetchState, action) {
  return Object.assign({}, fetchState, {[action.source]: true});
}

function fetchingDone(fetchState, action) {
  return Object.assign({}, fetchState, {[action.source]: false});
}

export default createReducer([], {
  REQUEST_FEED: fetchingBegin,
  REQUEST_NOTEBOOK: fetchingBegin,

  RECEIVE_FEED: fetchingDone,
  RECEIVE_NOTEBOOK: fetchingDone,
});
