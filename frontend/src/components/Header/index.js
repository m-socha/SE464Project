import React from 'react';
import {
  Navbar,
  NavItem,
  Icon,
} from 'react-materialize';
import Autocomplete from 'react-autocomplete'
import fuzzysearch from 'fuzzysearch'

import {fetchSearchResults} from 'actions/search'

export default class Header extends React.Component {
  constructor() {
    super()

    this.state = {
      inquiry: "",
      data: [],
      cache: new Map(),
    }
  }

  onChange(inquiry) {
    this.setState({inquiry});
    
    if (!inquiry) {
      return this.setState({data:[]});
    }

    let data = this.state.cache.get(inquiry);
    if (data) {
      return this.setState({data});
    }

    fetchSearchResults(inquiry, (data) => {
      if (!data) return;

      this.state.cache.set(inquiry, data);
      this.setState({data});
    });
  }

  onSelect(inquiry) {
    this.setState({inquiry});
  }

  renderItem(item, isHighlighted) {
    switch(item.type) {
      case 'users':
        return (
          <div key={item.id} style={{color: 'black'}}>
            {item.name}
          </div>
        );
      case 'courses':
        return (
          <div key={item.id} style={{color: 'black'}}>
            {item.code} {item.title}
          </div>
        );
      case 'notes':
        return (
          <div key={item.id} style={{color: 'black'}}>
            {item.data}
          </div>
        );
      case 'notebooks':
        return (
          <div key={item.name} style={{color: 'black'}}>
            {item.name}
          </div>
        );
      case 'comments':
        return (
          <div key={item.name} style={{color: 'black'}}>
            {item.name}
          </div>
        );
      default:
      ;  
    }

  }

  render() {
    return (
      <Navbar brand='WatNotes' right>
        <NavItem href='/'>Home</NavItem>
        <NavItem href='/login'>Login</NavItem>
        <li>
          <Icon>search</Icon>
        </li>
        <li>
          <Autocomplete
            autoHighlight={false} 
            getItemValue={(item) => item.label}
            items={this.state.data}
            value={this.state.inquiry}
            onChange={(e) => this.onChange(e.target.value)}
            onSelect={(val) => this.onSelect(val)}
            renderItem={this.renderItem}
          />
        </li>
      </Navbar>
    );
  }
}
