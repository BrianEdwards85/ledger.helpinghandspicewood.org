import React from 'react';
import ReactDOM from 'react-dom';
import Root from './components/Root';
import ApolloClient from 'apollo-boost';
import {ApolloProvider} from '@apollo/react-hooks';

const client = new ApolloClient({
  uri: '/graphql',
});

const App = () => (
  <ApolloProvider client={client}>
    <Root />
  </ApolloProvider>
);

ReactDOM.render(<App />, document.getElementById('root'));
