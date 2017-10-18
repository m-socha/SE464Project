import React from 'react';
import {
  Navbar,
  NavItem,
} from 'react-materialize';

export default class Header extends React.Component {
  render() {
    return (
      <Navbar className='row' brand='WatNotes' right>
        <NavItem href='/'>Home</NavItem>
        <NavItem href='/login'>Login</NavItem>
      </Navbar>
    );
  }
}
