import { get, post } from 'services/request';
import { REQUEST_COMMENTS, RECEIVE_COMMENTS, CREATE_COMMENT, CREATE_COMMENT_SUCCESS, 
  COMMENT_PANE_UPDATE, SELECT_PAGE_FOR_COMMENTS } from 'constants/data';

function requestComments(noteID) {
  return {
    type: REQUEST_COMMENTS,
    noteID,
    source: 'comments',
  };
}

function receiveComments(comments) {
  return {
    type: RECEIVE_COMMENTS,
    comments,
    source: 'comments',
  };
}

export function fetchComments(noteID, page) {
  return (dispatch) => {
    dispatch(requestComments(noteID));
    get(`/notes/${noteID}/comments`, { page, per_page: 20 }, (data, err) => {
      if (err) {
        console.log(`Error while fetching comments for ${noteID} - ${err}`);
      }

      dispatch(receiveComments(data.items));
    });
  };
}

function requestCreateComment(noteID) {
  return {
    type: CREATE_COMMENT,
    noteID,
    source: 'create_comment',
  };
}

function createCommentSuccess(noteID, newComment) {
  return {
    type: CREATE_COMMENT_SUCCESS,
    noteID,
    newComment,
    source: 'create_comment',
  };
}

export function createComment(noteID, content) {
  return (dispatch) => {
    dispatch(requestCreateComment(noteID));
    post(`/notes/${noteID}/comments`, { user_id: 1, content }, (response) => {
      dispatch(createCommentSuccess(noteID, response));
    });
  };
}

export function commentPageUpdate(commentPaneOpen) {
  return {
    type: COMMENT_PANE_UPDATE,
    commentPaneOpen,
  };
}

export function selectPageForComments(pageID) {
  return {
    type: SELECT_PAGE_FOR_COMMENTS,
    pageID,
  };
}
