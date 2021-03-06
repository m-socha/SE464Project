import React from 'react';
import { connect } from 'react-redux';
import {
  Preloader,
} from 'react-materialize';
import SplitPane from 'react-split-pane';
import { getNotebook } from 'selectors/notebook';
import styles from './notebook.css';
import TxtPage from 'components/TxtPage';
import ImagePage from 'components/ImagePage';
import CommentFeedContainer from '../../containers/CommentFeedContainer';
import { fetchNotebook } from 'actions/notebook';
import { fetchComments } from '../../actions/comment';
import { fetchUsers } from '../../actions/users';
import { createComment, commentPageUpdate, selectPageForComments } from 'actions/comment';

const scrollingStyle = {
  overflow: 'auto',
};
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
    this.props.fetchUsers();
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
            return <TxtPage selectPageForComments={this.props.selectPageForComments} onComment={this.props.onComment} key={page.id} page={page} />;
          case 'image/png':
            return <ImagePage selectPageForComments={this.props.selectPageForComments} onComment={this.props.onComment} key={page.id} page={page} format="png" />;
          case 'image/jpeg':
            return <ImagePage selectPageForComments={this.props.selectPageForComments} onComment={this.props.onComment} key={page.id} page={page} format="jpg" />;
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
        onChange={size => this.props.commentPaneUpdate(size)}
        pane1Style={scrollingStyle}>
        <div className={styles.NotebookFeed}>
          <div>
            <h3 className="center">{courseCode}</h3>
          </div>
          <div className="container">
            { pages }
          </div>
        </div>
        <CommentFeedContainer />
      </SplitPane>
    );
  }
}

function mapStateToProps(state) {
  return {
    isFetching: state.fetch.notebook,
    getNotebook: getNotebook.bind(null, state),
    commentPaneOpen: state.ui.commentPaneOpen,
  };
}

function mapDispatchToProps(dispatch) {
  return {
    fetchNotebook: (notebookID) => {
      dispatch(fetchNotebook(notebookID));
    },
    onComment: (noteID, content) => {
      dispatch(fetchComments(noteID, 1));
      dispatch(selectPageForComments(noteID));
      dispatch(createComment(noteID, content));
      dispatch(commentPageUpdate(true));
    },
    commentPaneUpdate: (paneSize) => {
      dispatch(commentPageUpdate((paneSize === 300)));
    },
    selectPageForComments: (pageID) => {
      dispatch(selectPageForComments(pageID));
      dispatch(fetchComments(pageID, 1));
      dispatch(commentPageUpdate(true));
    },
    fetchUsers: () => {
      dispatch(fetchUsers());
    },
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(NoteBook);
