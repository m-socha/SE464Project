import { combineReducers } from 'redux';

import fetch from './fetch';
import feed from './feed';
import pages from './pages';
import notebook from './notebook';
import comments from './comments';
import ui from './ui';

export default combineReducers({
  fetch,
  feed,
  notebook,
  comments,
  pages,
  ui,
});
