import React from 'react';
import { Link } from 'react-router-dom';
import { Card, Button } from 'react-materialize';
import styles from './card.css';

const NoteCard = ({ id, course, user }) => {
  const cardActions = [
    <Link key={id} to={`/notes/${id}`}>
      <Button className={`${styles.floatRight} red`} waves="light" icon="arrow_forward" />
    </Link>
  ];

  return (
		<Card className='small'
      title={course.title}
      actions={cardActions}>
		  { user &&
        <div>
          Author: {user.name}
        </div>
      }

      { course &&
        <div>
          Course: {course.code}
        </div>
      }
		</Card>
  );
}

export default NoteCard;
