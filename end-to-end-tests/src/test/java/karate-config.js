function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  const config = {
    env: env,
    AppUrl: '/api/flights/catalog' //The URL of the API
  };

  if (env === 'dev') {
    config.AppUrl = 'http://127.0.0.1:6070' + config.AppUrl //The entire URL with the host
  } else if (env === 'e2e') {
    config.AppUrl = 'http://127.0.0.1:6070' + config.AppUrl
  }

  return config;
}