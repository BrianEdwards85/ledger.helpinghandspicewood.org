const path = require('path');

module.exports = {
  entry: path.join(__dirname, 'src', 'main', 'js', 'app.jsx'),
  output: {
    path: path.join(__dirname, 'src', 'main', 'resource', 'public', 'js'),
    filename: 'main.js',
  },
  mode: 'development',
  resolve: {
    extensions: ['.jsx', '.js'],
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        include: [
          path.join(__dirname, 'src', 'main', 'js'),
        ],
        loader: 'babel-loader',
      },
      {
        test: /\.s[ac]ss$/i,
        use: ['style-loader', 'css-loader', 'sass-loader'],
      },
    ],
  },
};
