import { connect } from 'react-redux';
import CommentFeed from '../../components/CommentFeed';

function mapStateToProps(state) {
  const selectedPageId = state.ui.selectedPageForComments;
  const commentsToShow = state.pages.byId[selectedPageId] ? state.pages.byId[selectedPageId].comments : [];
  return {
    commentsToShow,
    comments: state.comments.byId,
  };
}

function mapDispatchToProps(dispatch) {
  return {
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(CommentFeed);
