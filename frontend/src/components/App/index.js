import React from 'react';
import {Navbar, NavItem} from 'react-materialize';

import Feed from 'container/Feed';

export default class App extends React.Component {
  constructor(props) {
    super();

    this.state ={
      page: 'Feed'
    };
  }

  renderPage() {
    switch(this.state.page) {
      case 'Feed':
        return (<Feed/>);
      default: // login page
        return (<div/>);
    }
  }

  render() {
    return (
      <div>
        <Navbar brand='WatNotes' right>
          <NavItem href=''>Home</NavItem>
          <NavItem href=''>Login</NavItem>
        </Navbar>
        {this.renderPage()}
      </div>
    );
  }
}
