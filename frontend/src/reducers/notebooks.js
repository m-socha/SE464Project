import {
  REQUEST_NOTEBOOKS,
  RECEIVE_NOTEBOOKS,
} from '../constants/data';

export default (state = {
  isFetching: false,
  didInvalidate: false,
  notebooks: [],
  userID: null,
  lastUpdated: Date.now(),
}, action) => {
  switch (action.type) {
    case REQUEST_NOTEBOOKS:
      return Object.assign({}, state, {
        isFetching: true,
        didInvalidate: false,
        userID: action.userID,
      });
    case RECEIVE_NOTEBOOKS:
      return Object.assign({}, state, {
        isFetching: false,
        didInvalidate: false,
        userID: action.userID,
        notebooks: action.notebooks,
        lastUpdated: action.receivedAt,
      });
    default:
      return state;
  }
};
