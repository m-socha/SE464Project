import React from 'react';
import styles from './commentfeed.css';
import Comment from '../Comment';

const CommentFeed = (props) => {
  const comments = props.commentsToShow.map(commentId =>
    <Comment key={commentId.toString()} comment={props.comments[commentId]} commenterName={props.users[props.comments[commentId].user_id].name}/>);

  return (
    <div className={styles.commentFeed}>
      {comments}
    </div>
  );
};

export default CommentFeed;
