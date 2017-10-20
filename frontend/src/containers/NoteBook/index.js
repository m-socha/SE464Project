import React from 'react';
import { connect } from 'react-redux';

import {getNotebook} from 'selectors/notebook';

import TxtPage from '../../components/TxtPage';
import PNGPage from '../../components/PNGPage';

import * as actionCreator from 'actions/notebook';

class NoteBook extends React.Component {
  componentWillMount() {
    this.props.fetchNotebook(this.props.match.params.note_id);
  }

  render() {
    if (this.props.isFetching != true) {

    }

    let notebook = this.props.getNotebook(this.props.match.params.note_id);
    return (
      <div className="container">
        <PNGPage data="https://www.w3schools.com/images/w3schools_green.jpg"/>
        <PNGPage data="https://www.w3schools.com/images/w3schools_green.jpg"/>
        <PNGPage data="https://www.w3schools.com/images/w3schools_green.jpg"/>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    isFetching: state.fetch['feed'],
    getNotebook: getNotebook.bind(null, state)
  };
}

export default connect(mapStateToProps, actionCreator)(NoteBook);