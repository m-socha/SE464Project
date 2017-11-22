import { combineReducers } from 'redux';
import { RECEIVE_USERS } from '../constants/data';
import { createReducer } from './util';

function receiveUsers(state, action) {
  const users = {};
  action.users.forEach((user) => {
    users[user.id] = user;
  });

  return {
    ...state,
    ...users,
  };
}

const usersByIdReducer = createReducer({}, {
  [RECEIVE_USERS]: receiveUsers,
});

export default combineReducers({
  byId: usersByIdReducer,
});
