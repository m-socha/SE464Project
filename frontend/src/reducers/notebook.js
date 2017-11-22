import { combineReducers } from 'redux';
import { RECEIVE_NOTEBOOK } from '../constants/data';
import { createReducer } from './util';

function receiveNotebook(state, action) {
  return {
    ...state,
    [action.notebookID]: {
      ...action.notebook,
    },
  };
}

const notebookByIdsReducer = createReducer({}, {
  [RECEIVE_NOTEBOOK]: receiveNotebook,
});

export default combineReducers({
  byId: notebookByIdsReducer,
});
