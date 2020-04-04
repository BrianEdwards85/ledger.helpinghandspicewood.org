import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Clients from './Clients/Clients';

const Routes = () => (
  <Switch>
    <Route path="/clients">
      <Clients/>
    </Route>
    <Route path="/">
      <div>Root</div>
    </Route>
  </Switch>
);

export default Routes;
