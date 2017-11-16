import React from 'react';
import Page from '../Page';

const TxtPage = (props) => {
  return (
    <Page onComment={props.onComment} pageID={props.page.id}>
      { props.page.data }
    </Page>
  );
};

export default TxtPage;
