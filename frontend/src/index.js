/* global document */
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import {
  Router,
  Route,
  Switch,
} from 'react-router-dom';
import createHistory from 'history/createBrowserHistory';
import configureStore from './store';
import Header from './components/Header';
import Feed from './containers/Feed';
import Note from './containers/Note';
import NotFound from './components/NotFound';
// import {AppContainer} from 'react-hot-loader'; // TODO: hot reloading

const store = configureStore({
  notebooks: {
    notebooks: [],
  },
}); // can pass an initial state
const history = createHistory();

const render = () => {
  ReactDOM.render(
    <Provider store={store}>
      <Router history={history}>
        <div>
          <Header />
          <Switch>
            <Route exact path="/" component={Feed} />
            <Route path="/notes/:note_id" component={Note} />
            <Route component={NotFound} />
          </Switch>
        </div>
      </Router>
    </Provider>,
    document.getElementById('root'),
  );
};

render();

// TODO: reimplement hot reloading
// if (module.hot) {
//   module.hot.accept('./components/App', () => { render(Feed); });
// }
