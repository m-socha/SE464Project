import { createReducer } from './util';
import { COMMENT_PANE_UPDATE, SELECT_PAGE_FOR_COMMENTS } from '../constants/data';

function updateCommentPane(state, action) {
  return {
    ...state,
    commentPaneOpen: action.commentPaneOpen,
  };
}

function updateSelectedPage(state, action) {
  return {
    ...state,
    selectedPageForComments: action.pageID,
  };
}

export default createReducer({}, {
  [COMMENT_PANE_UPDATE]: updateCommentPane,
  [SELECT_PAGE_FOR_COMMENTS]: updateSelectedPage,
});
