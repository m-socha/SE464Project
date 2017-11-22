import { combineReducers } from 'redux';
import { RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function receiveCommentsIds(state, action) {
  const comments = {};
  action.comments.forEach((comment) => {
    comments[comment.id] = comment;
  });

  return {
    ...state,
    ...comments,
  };
}

function createCommentSuccessIds(state, action) {
  return {
    ...state,
    [action.newComment.id]: action.newComment,
  };
}

function receiveCommentsAllIds(state, action) {
  const commentIds = action.comments.map(comment => comment.id);

  return [
    ...state,
    commentIds, // TODO: will have to merge, not concatenate here
  ];
}

function createCommentSuccessAllIds(state, action) {
  return [
    action.newComment.id,
    ...state,
  ];
}

const byIdsReducer = createReducer({}, {
  [RECEIVE_COMMENTS]: receiveCommentsIds,
  [CREATE_COMMENT_SUCCESS]: createCommentSuccessIds,
});

const allIdsReducer = createReducer([], {
  [RECEIVE_COMMENTS]: receiveCommentsAllIds,
  [CREATE_COMMENT_SUCCESS]: createCommentSuccessAllIds,
});

export default combineReducers({
  byId: byIdsReducer,
  allIds: allIdsReducer,
});
