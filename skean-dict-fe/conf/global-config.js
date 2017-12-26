import globalEnvArgs from '../dist/global-env-args.json'
import ConfigUtil from './ConfigUtil'

export default ConfigUtil.fetchConfigByMergingEnvArgs(globalEnvArgs)
