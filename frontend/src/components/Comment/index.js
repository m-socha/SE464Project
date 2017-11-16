import React from 'react';

class Comment extends React.Component {
  constructor(props) {
    super(props);
    this.state = { content: '' };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    this.props.onComment(this.state.content);
  }

  handleChange(event) {
    this.setState({ content: event.target.value });
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Add Comment:
          <textarea onChange={this.handleChange} />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

export default Comment;
