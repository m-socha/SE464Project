import { RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function receiveComments(state, action) {
  return {
    ...state,
  };
}

function createCommentSuccess(state, action) {
  return {
    ...state,
    [action.newComment.id]: action.newComment,
  };
}

export default createReducer({}, {
  RECEIVE_COMMENTS: receiveComments,
  CREATE_COMMENT_SUCCESS: createCommentSuccess,
});
