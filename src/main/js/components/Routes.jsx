import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Clients from './Clients/Clients';
import Client from './Client/Client';
import Categories from './Categories/Categories';

const Routes = () => (
    <Switch>
        <Route path="/clients/:client">
            <Client/>
        </Route>
        <Route path="/clients">
            <Clients/>
        </Route>
        <Route path="/categories">
            <Categories/>
        </Route>
        <Route path="/">
            <div>Root</div>
        </Route>
    </Switch>
);

export default Routes;
