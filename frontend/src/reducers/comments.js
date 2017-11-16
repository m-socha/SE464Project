import { RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function receiveComments(state, action) {
  return Object.assign([], state, action.comments); // change to object, with comment id as key
}

function createCommentSuccess(state, action) {
  return Object.assign([], state, action.newComment);
}

export default createReducer([], {
  RECEIVE_COMMENTS: receiveComments,
  CREATE_COMMENT_SUCCESS: createCommentSuccess,
});
