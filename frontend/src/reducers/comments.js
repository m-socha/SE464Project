import { RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function receiveComments(state, action) {
  return Object.assign([], state, action);
}

function createCommentSuccess(state, action) {
  console.log(action);
  console.log(state);
  return Object.assign([], state, action);
}

export default createReducer([], {
  RECEIVE_COMMENTS: receiveComments,
  CREATE_COMMENT_SUCCESS: createCommentSuccess,
});
