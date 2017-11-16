import { get, post } from 'services/request';
import { CREATE_COMMENT, CREATE_COMMENT_SUCCESS } from 'constants/data';


function requestCreateComment(noteID, value) {
  return {
    type: CREATE_COMMENT,
    noteID,
    value,
  };
}

function createCommentSuccess(noteID, newComment) {
  return {
    type: CREATE_COMMENT_SUCCESS,
    noteID,
    newComment,
  };
}

export function createComment(noteID, content) {
  return (dispatch) => {
    dispatch(requestCreateComment(noteID, content));
    post(`/notes/${noteID}/comments`, { user_id: 1, content }, (response) => {
      dispatch(createCommentSuccess(noteID, response));
    });
  };
}
