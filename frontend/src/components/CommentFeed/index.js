import React from 'react';
import styles from './commentfeed.css';
import Comment from '../Comment';

const CommentFeed = (props) => {
  const comments = props.commentsToShow.map(commentId =>
    <Comment key={commentId.toString()} comment={props.comments[commentId]} />);

  return (
    <div className={styles.commentFeed}>
      {comments}
    </div>
  );
};

export default CommentFeed;
