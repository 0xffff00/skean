const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const merge = require('webpack-merge')
const webpackBaseConfig = require('./webpack.base.config.js')

const ConfigUtil = require('./ConfigUtil')
const finalConfig = ConfigUtil.fetchConfigByMergingEnvArgs({env: 'dev', args: process.env})
ConfigUtil.writeConfigToFile(finalConfig)

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
    port: finalConfig.port
  },
  plugins: [
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
      template: './src/template/index.ejs',
      inject: false
    })
    // new FriendlyErrorsPlugin()
  ]
})
