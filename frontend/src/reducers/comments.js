import { RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { combineReducers } from 'redux';
import { createReducer } from './util';

function receiveCommentsIds(state, action) {
  // TODO
  // console.log('receiveCommentsIds')
  // console.log(action);
  return {
    ...state,
  };
}

function createCommentSuccessIds(state, action) {
  return {
    ...state,
    [action.newComment.id]: action.newComment,
  };
}

function receiveCommentsAllIds(state, action) {
  // TODO
  // console.log('receiveCommentsAllIds')
  // console.log(action);
  return {
    ...state,
  };
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
