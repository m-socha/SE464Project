import React from 'react';
import {Redirect} from 'react-router-dom';

import Card from '../../components/Card';

export default class Feed extends React.Component {
  constructor(props) {
    super();
  }

  render() {
    let data = [{note_id: '1', title: 'SE380 Fall 2017', author: 'ray', course: 'SE380'},
                {note_id: '2', title: 'SE380 Fall 2016', author: 'jay', course: 'SE380'},
                {note_id: '3', title: 'SE380 Fall 2015', author: 'say', course: 'SE380'}];
    const cards = data.map((note_data, i) => {
      return <Card key={i} {...note_data} />
    });

    return (
      <div className='container'>
        { cards }
      </div>
    );
  }
}
