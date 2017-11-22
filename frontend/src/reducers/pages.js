import { combineReducers } from 'redux';
import { RECEIVE_NOTEBOOK, RECEIVE_COMMENTS, CREATE_COMMENT_SUCCESS } from '../constants/data';
import { createReducer } from './util';

function receiveNotebook(state, action) {
  const pages = {};
  action.notebook.notes.forEach((page) => {
    pages[page.id] = {
      ...page,
      comments: [],
    };
  });

  return {
    ...state,
    ...pages,
  };
}

function receiveComments(state, action) {
  const commentIds = action.comments.map(comment => comment.id);

  return {
    ...state,
    [action.noteID]: {
      ...state[action.noteID],
      comments: commentIds,
    },
  };
}

function createCommentForPage(state, action) {
  const currentComments = state[action.newComment.note_id].comments;

  return {
    ...state,
    [action.newComment.note_id]: {
      ...state[action.newComment.note_id],
      comments: currentComments.concat(action.newComment.id),
    },
  };
}

const pagesByIdsReducer = createReducer({}, {
  [RECEIVE_NOTEBOOK]: receiveNotebook,
  [RECEIVE_COMMENTS]: receiveComments,
  [CREATE_COMMENT_SUCCESS]: createCommentForPage,
});

export default combineReducers({
  byId: pagesByIdsReducer,
});
