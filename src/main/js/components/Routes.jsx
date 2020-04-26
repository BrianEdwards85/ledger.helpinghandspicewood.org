import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Clients from './Clients/Clients';
import Client from './Client/Client';
import Categories from './Categories/Categories';
import EntryHistory from './EntryHistory/EntryHistory';

const Routes = () => (
    <Switch>
        <Route path="/categories">
            <Categories/>
        </Route>
        <Route path='/entries/:group'>
            <EntryHistory/>
        </Route>
        <Route path="/clients/:client">
            <Client/>
        </Route>
        <Route path="/clients">
            <Clients/>
        </Route>
        <Route path="/">
            <Clients/>
        </Route>
    </Switch>
);

export default Routes;
