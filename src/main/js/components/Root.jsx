import React from 'react';
import {useQuery} from '@apollo/react-hooks';
import {Card, Elevation, Spinner} from '@blueprintjs/core';
import {BrowserRouter as Router} from 'react-router-dom';
import Nav from './Nav/Nav';
import Routes from './Routes';
import {CURRENT_USER} from '../gql';
import './Root.scss';

const Root = () => {
  const {loading, error, data} = useQuery(CURRENT_USER);

  if (loading) return <div id="loading"><Spinner size="150"/></div>;
  if (error) return <p>Error :(</p>;

  return (
    <Router>
      <Nav user={data.current_user}/>
      <Card elevation={Elevation.ONE} className="app-pane">
        <Routes />
      </Card>
    </Router>
  );
};

export default Root;
