module.exports = {
  'root': true,
  'parserOptions': {
    'ecmaVersion': 6,
    'sourceType': 'module'
  },
  'env': {
    'browser': true
  },
  //"extends": "eslint:recommended",
  'extends': 'standard',
  'plugins': ['html'],
  'rules': {
    'quotes': ['error', 'single'],
    //"semi": ["error", "always"],
    'no-console': ['warn'],
    'no-unused-vars': 'off'
  }
}