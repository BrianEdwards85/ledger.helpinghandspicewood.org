/* eslint-disable camelcase */
import React, {useState} from 'react';
import {Icon, Button, Tooltip} from '@blueprintjs/core';
import _ from 'lodash';

import './Client.scss';

const augumentEntry = (entry) => {
    const {effective_date, values} = entry;
    const timestamp = Date.parse(effective_date);
    const value_map = _.chain(values)
        .groupBy('category_id')
        .mapValues(_.head)
        .mapValues('value')
        .value();

    return {
        ...entry,
        timestamp,
        value_map,
    };
};

const getCatagories = (entries) => _.chain(entries)
    .flatMap('values')
    .groupBy('category_id')
    .mapValues(_.head)
    .mapValues('description')
    .value();

const getTotals = (entries) => _.chain(entries)
    .flatMap('values')
    .groupBy('category_id')
    .mapValues((values) => _.chain(values)
        .map('value')
        .sum()
        .value())
    .value();

const notesButton = ({notes}) => {
    if (_.size(notes) === 0) {
        return <Button icon='clipboard' minimal={true} disabled={true}/>;
    }
    const [showNotes, setShowNotes] = useState(false);
    const toggleNotes = () => setShowNotes((x) => !x);

    return (
        <Tooltip isOpen={showNotes} content={notes}>
            <Button icon='clipboard' onClick={toggleNotes} minimal={true} intent="warning"/>
        </Tooltip>
    );
};
const renderEntry = (catagoryKeys, entry) => {
    return (
        <tr key={entry.id}>
            <td key={`${entry.id}_date`}>{entry.effective_date}</td>
            <td key={`${entry.id}_food`}>
                <Icon icon={entry.food ? 'tick' : 'cross'} />
            </td>
            <td key={`${entry.id}_total`}>{entry.entry_total}</td>
            {_.map(catagoryKeys, (catagory) =>
                <td key={`${entry.id}_${catagory}`}>
                    {catagory in entry.value_map ?
                        entry.value_map[catagory] : '-'}
                </td>)
            }
            <td key={`${entry.id}_actions`}>
                <Tooltip content='History'>
                    <Button icon='history' minimal={true} intent="primary"/>
                </Tooltip>
                <Tooltip content='Edit'>
                    <Button icon='edit' minimal={true} intent="success"/>
                </Tooltip>
                <Tooltip content='Archive'>
                    <Button icon='remove' minimal={true} intent="danger"/>
                </Tooltip>
                {notesButton(entry)}
            </td>
        </tr>);
};

const ClientDisplay = ({client}) => {
    const {name, family} = client;

    const entries = _.chain(client.entries)
        .map(augumentEntry)
        .sortBy(['timestamp'])
        .value();
    const catagories = getCatagories(entries);
    const catagoryKeys = _.chain(catagories)
        .keys()
        .sort()
        .value();
    const totals = getTotals(entries);

    const grandTotal = _.chain(totals)
        .values()
        .sum()
        .value();

    return (
        <React.Fragment>
            <h1>Client: {name}</h1>
            <div>Family: {family}</div>
            <h2>Visits</h2>
            <table
                className="bp3-html-table bp3-interactive bp3-html-table-striped bp3-html-table-bordered"
                style={{width: '95%'}}
                id="client_entries"
            >
                <thead>
                    <tr>
                        <th key="date">Date</th>
                        <th key="food">Food</th>
                        <th key="total">Total</th>
                        {_.map(catagoryKeys, (catagory) =>
                            <th key={catagory}>
                                {catagories[catagory]}
                            </th>)
                        }
                        <th key="actions"><Icon icon='build'/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr key='totals'>
                        <td key='totals'>Total</td>
                        <td key='totals_empty'></td>
                        <td key='grand_total'>{grandTotal}</td>
                        {_.map(catagoryKeys, (catagory) =>
                            <td key={`${catagory}_total`}>
                                {totals[catagory]}
                            </td>)
                        }
                        <td key='totals_actions'></td>
                    </tr>
                    {_.map(entries, _.partial(renderEntry, catagoryKeys))}
                </tbody>
            </table>
        </React.Fragment>
    );
};

export default ClientDisplay;
