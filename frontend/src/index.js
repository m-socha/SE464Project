/* global document */
import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter,
  Route,
  Link,
  Switch
} from 'react-router-dom';
import { AppContainer } from 'react-hot-loader';

import App from './components/App';
import NotFound from './components/NotFound';

const render = () => {
  ReactDOM.render(
    <AppContainer>
      <HashRouter>
        <Switch>
          <Route exact path="/" component={App} />
          <Route component={NotFound} />
        </Switch>
      </HashRouter>
    </AppContainer>,
    document.getElementById('root'),
  );
};

render();

if (module.hot) {
  module.hot.accept('./components/App', () => { render(App); });
}
