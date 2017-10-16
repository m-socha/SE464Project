import React from 'react';
import style from './card.css';

import { Col, Card, Button } from 'react-materialize';

export default class NoteCard extends React.Component {
  render() {
    return (
      <Col l={12} m={12} s={12}>
    		<Card className='small'
          title={this.props.title}
          actions={[<Button className={`${style.floatRight} 'red'`} waves='light' icon='arrow_forward' />]}>
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
      </Col>
    );
  }
}
