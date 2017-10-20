import React from 'react';
import styles from './page.css';

const Page = (props) => {
  return (
    <div className={styles.page}>
      { props.children }
    </div>
  );
};

export default Page;
