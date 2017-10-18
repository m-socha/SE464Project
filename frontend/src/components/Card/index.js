import React from 'react';
import { Link } from 'react-router-dom';
import { Card, Button } from 'react-materialize';
import './card.css';

const NoteCard = ({ id, user_id, course_id }) => {
  const cardActions = [
    <Link key={id} to={`/notes/${id}`}>
      <Button className="floatRight red" waves="light" icon="arrow_forward" />
    </Link>
  ];

  return (
		<Card className='small'
      title='HardCodedTitle'
      actions={cardActions}>
		  { user_id &&
        <div>
          Author: {user_id}
        </div>
      }

      { course_id &&
        <div>
          Course: {course_id}
        </div>
      }
		</Card>
  );
}

export default NoteCard;
