import React from 'react';
import Page from '../Page';
import styles from './imagePage.css';

import config from 'react-global-configuration';

const ImagePage = (props) => {
  return (
    <Page selectPageForComments={props.selectPageForComments} onComment={props.onComment} pageID={props.page.id}>
      <img className={styles.imagepage} src={`${config.get('backend')}/notes/${props.page.id}.${props.format}`} alt="Single Note" />
    </Page>
  );
};

export default ImagePage;
