import React from 'react';
import {Navbar, NavItem} from 'react-materialize';

import Feed from 'containers/Feed';

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
        {this.renderPage()}
      </div>
    );
  }
}
