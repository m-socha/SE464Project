import React from 'react';
import {
  Navbar,
  NavItem,
} from 'react-materialize';

<Navbar brand='WatNotes' right>
  <NavItem href='#/'>Home</NavItem>
  <NavItem href='#/login'>Login</NavItem>
</Navbar>

export default class Header extends React.Component {
  render() {
    return (
      <Navbar brand='WatNotes' right>
        <NavItem href='#/'>Home</NavItem>
        <NavItem href='#/login'>Login</NavItem>
      </Navbar>
    );
  }
}
