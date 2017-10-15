import React from 'react';

import {Navbar, NavItem} from 'react-materialize';

export default class App extends React.Component {
  render() {
    return (
      <Navbar brand='WatNotes' right>
        <NavItem href=''>Feed</NavItem>
        <NavItem href=''>Login</NavItem>
      </Navbar>
    );
  }
}
