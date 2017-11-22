import React from 'react';
import styles from './comment.css';

const Comment = props => (
  <div className={styles.comment}>{props.comment.content}</div>
);

export default Comment;
