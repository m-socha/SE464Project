import React from 'react';
import styles from './page.css';
import AddComment from '../AddComment';

const Page = (props) => {
  return (
    <div>
      <div className={styles.page}>
        { props.children }
      </div>
      <AddComment onComment={value => props.onComment(props.pageID, value)}/>
    </div>
  );
};

export default Page;
