const _ = require('lodash')
const devConfig = require('./global-config.dev.json')
const prodConfig = require('./global-config.prod.json')

function fetchConfigByMergingEnvArgs (globalEnvArgs) {
  let conf = prodConfig
  if (globalEnvArgs && globalEnvArgs.env === 'dev') conf = _.merge(conf, devConfig)
  if (globalEnvArgs && globalEnvArgs.args) {
    Object.keys(conf).forEach(key => {
      // only support simple arg name like 'npm_config_xxx'
      let nck = 'npm_config_' + key
      let v = globalEnvArgs.args[nck]
      if (v) conf[key] = v
    })
  }
  return conf
}

module.exports = {
  fetchConfigByMergingEnvArgs
}
