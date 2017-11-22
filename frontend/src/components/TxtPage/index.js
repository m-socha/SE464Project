import React from 'react';
import Page from '../Page';

const TxtPage = (props) => {
  return (
    <Page selectPageForComments={props.selectPageForComments} onComment={props.onComment} pageID={props.page.id}>
      { props.page.data }
    </Page>
  );
};

export default TxtPage;
