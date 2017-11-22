import { combineReducers } from 'redux';
import { RECEIVE_NOTEBOOK, RECEIVE_COMMENTS } from '../constants/data';
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

const pagesByIdsReducer = createReducer({}, {
  [RECEIVE_NOTEBOOK]: receiveNotebook,
  [RECEIVE_COMMENTS]: receiveComments,
});

export default combineReducers({
  byId: pagesByIdsReducer,
});
