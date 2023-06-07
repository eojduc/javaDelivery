const path = require("path")
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
  entry: "./src/index.js",
  mode: 'development',
  output: {
    path: __dirname,
    filename: '../src/main/resources/static/built/bundle.js'
  },
  module: {
    rules: [
      {
        test: path.join(__dirname, '.'),
        exclude: /(node_modules)/,
        use: [{
          loader: 'babel-loader',
          options: {
            presets: ["@babel/preset-env", "@babel/preset-react"]
          }
        }]
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin()
  ]
};