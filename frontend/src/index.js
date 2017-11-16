/* global document */
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import {
  Router,
  Route,
  Switch,
  HashRouter,
} from 'react-router-dom';

import configureStore from './store';
import Header from './components/Header';
import Feed from './containers/Feed';
import Note from './containers/NoteBook';
import NotFound from './components/NotFound';
// import {AppContainer} from 'react-hot-loader'; // TODO: hot reloading

import config from 'react-global-configuration';
import configuration from '../config';

config.set(configuration);

const store = configureStore({}); 

const render = () => {
  ReactDOM.render(
    <Provider store={store}>
      <HashRouter>
        <div>
          <Header/>
          <Switch>
            <Route exact path="/" component={Feed} />
            <Route path="/notes/:note_id" component={Note} />
            <Route component={NotFound} />
          </Switch>
        </div>
      </HashRouter>
    </Provider>,
    document.getElementById('root'),
  );
};

render();

// TODO: reimplement hot reloading
// if (module.hot) {
//   module.hot.accept('./components/App', () => { render(Feed); });
// }
