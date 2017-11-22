import React from 'react';

class AddComment extends React.Component {
  constructor(props) {
    super(props);
    this.state = { content: '' };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    this.props.onComment(this.state.content);
    this.setState({ content: '' });
  }

  handleChange(event) {
    this.setState({ content: event.target.value });
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Add Comment:
          <textarea value={this.state.content} onChange={this.handleChange} />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

export default AddComment;
