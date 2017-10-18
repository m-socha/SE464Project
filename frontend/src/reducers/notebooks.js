import {
  REQUEST_NOTEBOOKS,
  RECEIVE_NOTEBOOKS,
} from '../constants/data';

export default (state = {
  isFetching: false,
  didInvalidate: false,
  notebooks: [],
  user_id: null,
  lastUpdated: Date.now(),
}, action) => {
  switch (action.type) {
    case REQUEST_NOTEBOOKS:
      return Object.assign({}, state, {
        isFetching: true,
        didInvalidate: false,
      });
    case RECEIVE_NOTEBOOKS:
      return Object.assign({}, state, {
        isFetching: false,
        didInvalidate: false,
        user_id: action.user_id,
        notebooks: action.notebooks,
        lastUpdated: action.receivedAt,
      });
    default:
      return state;
  }
};
