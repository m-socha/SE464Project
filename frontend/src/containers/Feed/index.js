import React from 'react';
import { connect } from 'react-redux';
import { Col } from 'react-materialize';
import Card from '../../components/Card';
import fetchNotebooks from '../../actions';

class Feed extends React.Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch(fetchNotebooks(1)); // hardcode 1 as user_id
  }

  render() {
    const { notebooks } = this.props;
    const cards = notebooks.map(notebookData =>
      <Card key={notebookData.notebook_id} {...notebookData} />);

    return (
      <div className="container">
        <Col l={12} m={12} s={12}>
          { cards }
        </Col>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  notebooks: state.notebooks,
});

export default connect(mapStateToProps)(Feed);

// TODO add proptypes
