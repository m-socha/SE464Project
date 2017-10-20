import React from 'react';
import Page from '../Page';

const TxtPage = (props) => {
  return (
    <Page>
      { props.page.data }
    </Page>
  );
};

export default TxtPage;
