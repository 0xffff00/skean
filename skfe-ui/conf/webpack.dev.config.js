const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const merge = require('webpack-merge')
const webpackBaseConfig = require('./webpack.base.config.js')

const globalConfig = require('./conf-util.js').fetchConfigByMergingEnvArgs({env: 'dev', args: process.env})

module.exports = merge(webpackBaseConfig, {
  // devtool: '#source-map',
  devtool: '#cheap-module-eval-source-map',
  output: {
    publicPath: '/dist/',
    filename: '[name].js',
    chunkFilename: '[name].chunk.js'
  },
  node: {
    fs: 'empty'
  },
  devServer: {
    port: globalConfig.port
  },
  plugins: [
    new webpack.DefinePlugin({
      'GLOBAL_CONFIG': JSON.stringify(globalConfig)
    }),
    new ExtractTextPlugin({
      filename: '[name].css',
      allChunks: true
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendors',
      filename: 'vendors.js'
    }),
    new HtmlWebpackPlugin({
      filename: '../index.html',
      template: './src/assets/index.ejs',
      inject: false
    })
    // new FriendlyErrorsPlugin()
  ]
})
