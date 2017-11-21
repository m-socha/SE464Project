import React from 'react';
import { connect } from 'react-redux';
import {
  Preloader,
} from 'react-materialize';
import SplitPane from 'react-split-pane';
import {getNotebook} from 'selectors/notebook';
import styles from './notebook.css';

import TxtPage from 'components/TxtPage';
import ImagePage from 'components/ImagePage';

import { fetchNotebook } from 'actions/notebook';
import { createComment, commentPageUpdate } from 'actions/comment';

const resizerStyle = {
  width: '11px',
  borderLeft: '5px solid rgba(255, 255, 255, 0)',
  borderRight: '5px solid rgba(255, 255, 255, 0)',
  cursor: 'col-resize',
  background: '#000',
  opacity: '.2',
};

class NoteBook extends React.Component {
  componentWillMount() {
    this.props.fetchNotebook(this.props.match.params.note_id);
  }

  render() {
    if (this.props.isFetching) {
      return (
        <div className="center">
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
      <SplitPane
        split="vertical"
        maxSize={300}
        minSize={0}
        defaultSize={this.props.commentPaneOpen ? 300 : 0}
        step={300}
        primary="second"
        resizerStyle={resizerStyle}
        onChange={size => this.props.commentPaneUpdate(size)}>
        <div>
          <div>
            <h3 className="center">{courseCode}</h3>
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
    getNotebook: getNotebook.bind(null, state),
    commentPaneOpen: state.commentPaneOpen,
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
    commentPaneUpdate: (paneSize) => {
      const paneOpen = (paneSize === 300);
      dispatch(commentPageUpdate(paneOpen));
    },
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(NoteBook);
