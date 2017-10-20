import React from 'react';
import { connect } from 'react-redux';

import * as actionCreator from 'actions/notes';

class NoteBook extends React.Component {
  render() {
    return <h1>TBD</h1>
  }
}

const mapStateToProps = (state) => ({})

export default connect(mapStateToProps, actionCreator)(NoteBook);