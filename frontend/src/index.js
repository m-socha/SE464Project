/* global document */
import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter,
  Route,
  Link,
  Switch
} from 'react-router-dom';
import {AppContainer} from 'react-hot-loader';
import {Provider} from 'react-redux';
import {createStore} from 'redux';

import App from './components/App';
import NotFound from './components/NotFound';

import rootReducer from './reducers';

const render = () => {
  ReactDOM.render(
    <Provider store={createStore(rootReducer)}>
      <AppContainer>
        <HashRouter>
          <Switch>
            <Route exact path="/" component={App} />
            <Route component={NotFound} />
          </Switch>
        </HashRouter>
      </AppContainer>
    </Provider>,
    document.getElementById('root'),
  );
};

render();

if (module.hot) {
  module.hot.accept('./components/App', () => { render(); });
}
