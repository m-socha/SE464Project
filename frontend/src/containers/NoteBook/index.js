import React from 'react';
import { connect } from 'react-redux';
import {
  Icon,
  Preloader,
} from 'react-materialize';
import SplitPane from 'react-split-pane';
import {getNotebook} from 'selectors/notebook';
import styles from './notebook.css';

import TxtPage from 'components/TxtPage';
import ImagePage from 'components/ImagePage';

import { fetchNotebook } from 'actions/notebook';
import { createComment } from 'actions/comment';

class NoteBook extends React.Component {
  componentWillMount() {
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
            return <TxtPage onComment={this.props.onComment} key={page.id} page={page} />;
          case 'image/png':
            return <ImagePage onComment={this.props.onComment} key={page.id} page={page} format="png" />;
          case 'image/jpeg':
            return <ImagePage onComment={this.props.onComment} key={page.id} page={page} format="jpg" />;
          default:
            return null;
        }
      });
    }
    
    const courseCode = this.props.location.state ? this.props.location.state.courseCode : 'Course Code';

    return (
      <SplitPane split="vertical" minSize={200} defaultSize={200} primary="second">
      <div>
        <div>
          {/*<Icon small>keyboard_arrow_left</Icon>*/}
          <h3 className={styles.center}>{courseCode}</h3>
        </div>
        <div className="container">
          { pages }
        </div>
      </div>
       <div>Comments</div>
       </SplitPane>
    );
  }
}

function mapStateToProps(state) {
  return {
    isFetching: state.fetch['notebook'],
    getNotebook: getNotebook.bind(null, state)
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchNotebook: (notebookID) => {
      dispatch(fetchNotebook(notebookID));
    },
    onComment: (noteID, content) => {
      dispatch(createComment(noteID, content));
    },
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(NoteBook);
