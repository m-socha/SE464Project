import { REQUEST_NOTEBOOK, REQUEST_NOTEBOOKS, REQUEST_COMMENTS, CREATE_COMMENT,
    RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function fetchingBegin(fetchState, action) {
  return Object.assign({}, fetchState, {[action.source]: true});
}

function fetchingDone(fetchState, action) {
  return Object.assign({}, fetchState, {[action.source]: false});
}

export default createReducer([], {
  REQUEST_FEED: fetchingBegin,
  REQUEST_NOTEBOOK: fetchingBegin,
  REQUEST_COMMENTS: fetchingBegin,
  CREATE_COMMENT: fetchingBegin,

  RECEIVE_FEED: fetchingDone,
  RECEIVE_NOTEBOOK: fetchingDone,
  RECEIVE_COMMENTS: fetchingDone,
  CREATE_COMMENT_SUCCESS: fetchingDone,
});
