import React from 'react';
import { connect } from 'react-redux';
import {
  Icon,
  Preloader,
} from 'react-materialize';
import {getNotebook} from 'selectors/notebook';
import styles from './notebook.css';

import TxtPage from 'components/TxtPage';
import ImagePage from 'components/ImagePage';

import * as actionCreator from 'actions/notebook';

class NoteBook extends React.Component {
  componentWillMount() {
    console.log(this.props);
    this.props.fetchNotebook(this.props.match.params.note_id);
  }

  render() {
    if (this.props.isFetching) {
      return (
        <div className={styles.center}>
          <Preloader size="big"/>;
        </div>
      );
    }

    const notebook = this.props.getNotebook(this.props.match.params.note_id);
    let pages = <div />;

    if (typeof notebook !== 'undefined') {
      pages = notebook.notes.map((page) => {
        switch (page.format) {
          case 'text/plain':
            return <TxtPage key={page.id} page={page} />;
          case 'image/png':
            return <ImagePage key={page.id} page={page} format="png" />;
          case 'image/jpeg':
            return <ImagePage key={page.id} page={page} format="jpg" />;
          default:
            return null;
        }
      });
    }
    
    const courseCode = this.props.location.state ? this.props.location.state.courseCode : 'Course Code';

    return (
      <div>
        <div>
          {/*<Icon small>keyboard_arrow_left</Icon>*/}
          <h3 className={styles.center}>{courseCode}</h3>
        </div>
        <div className="container">
          { pages }
        </div>
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