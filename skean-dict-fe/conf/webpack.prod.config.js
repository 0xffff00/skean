const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const merge = require('webpack-merge')
const webpackBaseConfig = require('./webpack.base.config.js')

const ConfigUtil = require('./ConfigUtil')
const finalConfig = ConfigUtil.fetchConfigByMergingEnvArgs({env: 'dev', args: process.env})
ConfigUtil.writeConfigToFile(finalConfig)

module.exports = merge(webpackBaseConfig, {
  output: {
    publicPath: '/dist/',
    filename: '[name].[hash].js',
    chunkFilename: '[name].[hash].chunk.js'
  },
  plugins: [
    new ExtractTextPlugin({
      filename: '[name].[hash].css',
      allChunks: true
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendors',
      filename: 'vendors.[hash].js'
    }),
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      }
    }),
    new HtmlWebpackPlugin({
      filename: '../index_prod.html',
      template: './src/template/index.ejs',
      inject: false
    })
  ]
})