import React from 'react';
import Page from '../Page';
import styles from './imagePage.css';

const ImagePage = (props) => {
  return (
    <Page>
      <img className={styles.imagepage} src={`http://watnotes.herokuapp.com/notes/${props.page.id}.${props.format}`} alt="Single Note" />
    </Page>
  );
};

export default ImagePage;
