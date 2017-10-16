/* global document */
import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter,
  Route,
  Link,
  Switch
} from 'react-router-dom';
import {AppContainer} from 'react-hot-loader';
import {Navbar, NavItem} from 'react-materialize';
import {Provider} from 'react-redux';
import {createStore} from 'redux';

import Note from './containers/Note';
import Feed from './containers/Feed';

import NotFound from './components/NotFound';

import rootReducer from './reducers';

const render = () => {
  ReactDOM.render(
    <div>
      <Navbar brand='WatNotes' right>
        <NavItem href='#/'>Home</NavItem>
        <NavItem href='#/login'>Login</NavItem>
      </Navbar>
      
      <Provider store={createStore(rootReducer)}>
        <AppContainer>
          <HashRouter>
            <Switch>
              <Route exact path="/" component={Feed} />
              <Route path="/notes/:note_id" component={Note} />
              <Route component={NotFound} />
            </Switch>
          </HashRouter>
        </AppContainer>
      </Provider>
    </div>,
    document.getElementById('root'),
  );
};

render();

if (module.hot) {
  module.hot.accept('./components/App', () => { render(); });
}
