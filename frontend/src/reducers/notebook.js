import { RECEIVE_NOTEBOOK } from '../constants/data';
import { createReducer } from './util';

function receiveNotebook(notebookState, action) {
  return Object.assign({}, notebookState, { [action.notebookID]: action.notebook });
}

export default createReducer([], {
  RECEIVE_NOTEBOOK: receiveNotebook,
});
