import React from 'react';
import Card from '../../components/Card';

export default class Feed extends React.Component {
  constructor(props) {
    super();
  }

  render() {
    let data = [{note_id: '1', title: 'SE380 Fall 2018', author: 'ray', course: 'SE380'},
                {note_id: '2', title: 'SE380 Fall 2018', author: 'ray', course: 'SE380'},
                {note_id: '3', title: 'SE380 Fall 2018', author: 'ray', course: 'SE380'}];
    const cards = data.map((note_data) => {
      return <Card key={note_data.note_id} title={note_data.title} author={note_data.author} course={note_data.course}/>
    });

    return (
      <div className='container'>
        { cards }
      </div>
    );
  }
}
