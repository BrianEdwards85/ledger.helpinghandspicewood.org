import React, {useState} from 'react';
import {Icon, Button, Tooltip} from '@blueprintjs/core';
import _ from 'lodash';
import {
    getCatagoriesAndKeys,
    renderCatagoryHeaders,
    renderEntryValues,
    formatCurrency,
    augumentEntry,
} from '../common/entries';

import './Client.scss';

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
            <td key={`${entry.id}_total`}>{formatCurrency(entry.entry_total)}</td>
            {renderEntryValues(catagoryKeys, entry)}
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
    const {catagories, keys} = getCatagoriesAndKeys(entries);
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
                        {renderCatagoryHeaders(catagories, keys)}
                        <th key="actions"><Icon icon='build'/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr key='totals'>
                        <td key='totals'>Total</td>
                        <td key='totals_empty'></td>
                        <td key='grand_total'>{formatCurrency(grandTotal)}</td>
                        {_.map(keys, (catagory) =>
                            <td key={`${catagory}_total`}>
                                {formatCurrency(totals[catagory])}
                            </td>)
                        }
                        <td key='totals_actions'></td>
                    </tr>
                    {_.map(entries, _.partial(renderEntry, keys))}
                </tbody>
            </table>
        </React.Fragment>
    );
};

export default ClientDisplay;
