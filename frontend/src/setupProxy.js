const proxy = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(proxy('/api/login', { target: 'http://localhost:4040/' }));
  app.use(proxy('/notification-rule-management/**/*', { target: 'http://localhost:8080/' }));
};
