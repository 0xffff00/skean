const fs = require('fs')
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

function writeConfigToFile (config, path = 'dist/global-config-233.json') {
  if (!fs.existsSync('dist')) {
    fs.mkdirSync('dist')
  }
  fs.open(path, 'w', function (err1, fd) {
    const buf = JSON.stringify(config, null, 4)
    fs.write(fd, buf, 0, buf.length, 0, function (err1, written, buffer) {})
  })
}

module.exports = {
  writeConfigToFile,
  fetchConfigByMergingEnvArgs
}
// export default {
//   writeConfigToFile,
//   fetchConfigByMergingEnvArgs
// }
