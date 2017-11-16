import React from 'react';
import styles from './page.css';
import Comment from '../Comment';

const Page = (props) => {
  return (
    <div>
      <div className={styles.page}>
        { props.children }
      </div>
      <Comment onComment={value => props.onComment(props.pageID, value)}/>
    </div>
  );
};

export default Page;
