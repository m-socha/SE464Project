import React from 'react';
import style from './card.css';

import { Link } from 'react-router-dom';
import { Card, Button } from 'react-materialize';

export default class NoteCard extends React.Component {
  render() {
    let cardActions = 
      [ <Link to={`/notes/${this.props.note_id}`}>
          <Button className={`${style.floatRight} 'red'`} waves='light' icon='arrow_forward' />
        </Link>
      ];

    return (
  		<Card className='small'
        title={this.props.title}
        actions={cardActions}>
  		  { this.props.author &&
          <div>
            Author: {this.props.author}
          </div>
        }

        { this.props.course &&
          <div>
            Course: {this.props.course}
          </div>
        }
  		</Card>
    );
  }
}
