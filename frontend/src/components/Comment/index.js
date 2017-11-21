import React from 'react';
import styles from './comment.css';

const Comment = props => (
  <div className={styles.comment}>{props.commentText}</div>
);

export default Comment;
