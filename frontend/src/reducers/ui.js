import { createReducer } from './util';

function updateCommentPane(state, action) {
  return {
    ...state,
    commentPaneOpen: action.commentPaneOpen,
  };
}

export default createReducer({}, {
  COMMENT_PANE_UPDATE: updateCommentPane,
});
