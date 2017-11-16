import { combineReducers } from 'redux';

import fetch from './fetch';
import feed from './feed';
import notebook from './notebook';
import comments from './comments';

export default combineReducers({
  fetch,
  feed,
  notebook,
  comments,
});
