import { get } from '../services/request';
import { REQUEST_USERS, RECEIVE_USERS } from '../constants/data';

function requestUsers() {
  return {
    type: REQUEST_USERS,
    source: 'users',
  };
}

function receiveUsers(users) {
  return {
    type: RECEIVE_USERS,
    users,
    source: 'users',
  };
}

export function fetchUsers(page) {
  return (dispatch) => {
    dispatch(requestUsers());
    get('/users', { page, per_page: 20 }, (data, err) => {
      if (err) {
        console.log(`Error while fetching users - ${err}`);
      }

      dispatch(receiveUsers(data.items));
    });
  };
}
