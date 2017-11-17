import React from 'react';
import {
  Navbar,
  NavItem,
  Icon,
} from 'react-materialize';
import Autocomplete from 'react-autocomplete'
import fuzzysearch from 'fuzzysearch'


export default class Header extends React.Component {
  constructor() {
    super()

    this.state = {
      inquiry: "",
      data: [
              {label: 'Apple'},
              {label: 'Microsoft'},
              {label: 'Google'}
            ]
    }
  }

  onChange(inquiry) {
    this.setState({inquiry});
  }

  onSelect(inquiry) {
    this.setState({inquiry});
  }

  renderItem(item, isHighlighted) {
    return (
      <div key={item.label} style={{color: 'black'}}>
        {item.label}
      </div>
    );
  }

  shouldItemRender(item) {
    let match = fuzzysearch(this.state.inquiry.toLowerCase(), item.label.toLowerCase());
    return match;
  }

  render() {
    return (
      <Navbar className='row' brand='WatNotes' right>
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
            shouldItemRender={this.shouldItemRender.bind(this)}
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
