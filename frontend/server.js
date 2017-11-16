const http = require('http');
const express = require('express');
const httpProxy = require('http-proxy');
const path = require('path');
const config = require('react-global-configuration');
const configuration = require('./config');

config.set(configuration);

const proxy = httpProxy.createProxyServer({});
const app = express();

// configure webpack with hot reloading
(function initWebpack() {
    const webpack = require('webpack');
    const webpackConfig = require('./webpack/common.config');

    const compiler = webpack(webpackConfig);

    app.use(require('webpack-dev-middleware')(compiler, {
        noInfo: true, publicPath: webpackConfig.output.publicPath,
    }));

    app.use(require('webpack-hot-middleware')(compiler, {
        log: console.log, path: '/__webpack_hmr', heartbeat: 10 * 1000,
    }));

    app.use(express.static(path.join(__dirname, '/')));
}());

// api routes are proxied to our backend
app.all(/^\/api\/(.*)/, (req, res) => {
  res.send({ page: 1, total_pages: 1, total_results: 3, items: [{ id: 1, user_id: 1, course_id: 1 },
    { id: 2, user_id: 1, course_id: 2 }, { id: 3, user_id: 1, course_id: 3 }]});
  // proxy.web(req, res, { target: 'http://localhost:5000' });
});
app.get(/.*/, (req, res) => {
  res.sendFile(path.join(__dirname, '/index.html'));
});

// start the server
const server = http.createServer(app);
server.listen(process.env.PORT || 3000, () => {
    const address = server.address();
    console.log('Listening on: %j', address);
    console.log(' -> that probably means: http://localhost:%d', address.port);
});
