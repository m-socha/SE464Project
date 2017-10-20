import { RECEIVE_NOTEBOOK } from '../constants/data';
import {createReducer} from './util';

function receiveNotebook(notebookState, action) {
  return Object.assign({}, notebookState, {[action.notebookId]: action.notebook});
}

export default createReducer([], {
  RECEIVE_NOTEBOOK: receiveNotebook,
});