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

    const notebook = this.props.getNotebook(this.props.match.params.note_id);
    let pages = <div />;

    if (typeof notebook !== 'undefined') {
      pages = notebook.notes.map((page) => {
        switch (page.format) {
          case 'text':
            return <TxtPage key={page.id} page={page} />;
          case 'png':
            return <PNGPage key={page.id} page={page} />;
          default:
            return <TxtPage key={page.id} page={page} />;
        }
      });
    }

    return (
      <div className="container">
        { pages }
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    isFetching: state.fetch['notebook'],
    getNotebook: getNotebook.bind(null, state)
  };
}

export default connect(mapStateToProps, actionCreator)(NoteBook);