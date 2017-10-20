import React from 'react';
import Page from '../Page';

const PNGPage = (props) => {
  return (
    <Page>
      <img src={props.data} alt="Note" />
    </Page>
  );
};

export default PNGPage;
