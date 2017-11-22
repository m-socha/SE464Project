import React from 'react';
import styles from './comment.css';

const Comment = props => (
  <div className={styles.comment}>
    <p>{props.comment.content}</p>
    <br />
    <p className={styles.commenterName}>{props.commenterName}</p>
  </div>
);

export default Comment;
