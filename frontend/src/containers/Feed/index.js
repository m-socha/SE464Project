import React from 'react';
import { connect } from 'react-redux';
import {
  Col,
  Preloader,
} from 'react-materialize';
import styles from './feed.css';
import Card from 'components/Card';
import * as actionCreator from 'actions/feed';

class Feed extends React.Component {
  constructor() {
    super();

    this.state = {
      page: 1
    }
  }

  componentWillMount() {
    this.props.fetchFeed(1, this.state.page);
  }

  render() {
    if (this.props.isFetching) {
      return (
        <div className={styles.center}>
          <Preloader size="big" />
        </div>
      );
    }

    const { feed } = this.props;
    const cards = feed.map(notebook => <Card key={notebook.id} {...notebook} />);

    return (
      <div className="container">
        { !this.props.isFetching &&
          <Col l={12} m={12} s={12}>
            { cards }
          </Col>
        }
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    feed: state.feed,
    isFetching: state.fetch['feed'],
  }
}

export default connect(mapStateToProps, actionCreator)(Feed);
