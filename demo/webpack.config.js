const { VueLoaderPlugin } = require("vue-loader");
var path = require('path')
var webpack = require('webpack')

module.exports = {
  mode: "production",
  entry: './src/main.js',
  output: {
    path: path.resolve(__dirname, './src/main/resources/static/dist'),
    publicPath: '/dist/',
    filename: 'build.js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader',
      },
      {
        test: /\.(eot|svg|ttf|woff|woff2)(\?\S*)?$/,
        loader: 'file-loader'
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
        	  js: [
                  { loader: 'cache-loader' },
                  { loader: 'babel-loader', options: { presets: ['env'] } }
                ]
          }
          // other vue-loader options go here
        }
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
        options: {
            presets: ['@babel/preset-env'] // 変更前は 'env'
        },
        query: {
            cacheDirectory: true,
            presets: ['es2015']
        }

      },
      {
        test: /\.(png|jpg|gif|svg)$/,
        loader: 'file-loader',
        options: {
          name: '[name].[ext]?[hash]'
        }
      }
    ]
  },
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    },
    modules: [
      path.resolve('./node_modules')
    ],
    extensions: ['*', '.js', '.vue', '.json']
  },
  devServer: {
    historyApiFallback: true,
    noInfo: true,
    overlay: true
  },
  performance: {
    hints: false
  },
  devtool: '#eval-source-map',
  plugins: [
       // Vueを読み込めるようにするため
       new VueLoaderPlugin()
  ],
}
