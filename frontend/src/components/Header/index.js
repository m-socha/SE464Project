import React from 'react';
import {
  Navbar,
  NavItem,
  Input,
  Icon
} from 'react-materialize';

export default class Header extends React.Component {
  constructor() {
    super()

    this.state = {
      inquiry: ""
    }
  }

  onChange(proxy, inquiry) {
    this.setState({inquiry});
  }

  render() {
    return (
      <Navbar className='row' brand='WatNotes' right>
        <Input label="Search docs" value={this.state.inquiry} onChange={this.onChange.bind(this)}>
          <Icon>search</Icon>
        </Input>
        <NavItem href='/'>Home</NavItem>
        <NavItem href='/login'>Login</NavItem>
      </Navbar>
    );
  }
}
