import React from 'react';
import {
  Navbar,
  NavItem,
  Icon,
} from 'react-materialize';
import Autocomplete from 'react-autocomplete'
import fuzzysearch from 'fuzzysearch'
import { Link } from 'react-router-dom';

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
      if (this.state.inquiry === inquiry) {
        this.setState({data});
      }
    });
  }

  onSelect(val, item) {
    let inquiry = item.name || item.code || item.data;
    // this.setState({inquiry});
  }

  renderItem(item, isHighlighted) {
    let label = item.name;
    switch(item.type) {
      case 'users':
        label = item.name;
        if (label.length > 25) {
          label = label.substring(0, 15) + '...';
        }
        return (
            <a key={item.id} style={{color: 'black', fontSize: '10px'}}>
              {label}
            </a>
        );
      case 'courses':
        label = item.code + ' ' + item.title;
        if (label.length > 25) {
          label = label.substring(0, 15) + '...';
        }
        return (
          <a key={item.id} style={{color: 'black', fontSize: '10px'}}>
            {label}
          </a>
        );
      case 'notes':
        label = item.data;
        if (label.length > 25) {
          label = label.substring(0, 15) + '...';
        }
        return (
          <Link key={item.id} to={{ pathname: `/notes/${item.notebook_id}`}} style={{color: 'black', fontSize: '10px'}}>
            {label}
          </Link>
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
            onSelect={(val, item) => this.onSelect(val, item)}
            renderItem={this.renderItem}
          />
        </li>
      </Navbar>
    );
  }
}
