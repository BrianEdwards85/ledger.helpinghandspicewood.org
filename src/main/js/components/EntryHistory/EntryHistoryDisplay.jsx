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
            <td key={`${entry.id}_current`}>
                <Icon icon={entry.current ? 'tick' : 'cross'} />
            </td>
            <td key={`${entry.id}_date`}>{entry.added_on}</td>
            <td key={`${entry.id}_food`}>
                <Icon icon={entry.food ? 'tick' : 'cross'} />
            </td>
            <td key={`${entry.id}_total`}>{formatCurrency(entry.entry_total)}</td>
            {renderEntryValues(catagoryKeys, entry)}
        </tr>);
};

const EntryHistoryDisplay = ({group_entries}) => {
    const entries = _.chain(group_entries)
        .map(augumentEntry)
        .sortBy(['added_timestamp'])
        .reverse()
        .value();
    const {catagories, keys} = getCatagoriesAndKeys(entries);
    return (
        <React.Fragment>
            <h1>{`History: ${group_entries[0].group}`}</h1>
            <table
                className="bp3-html-table bp3-interactive bp3-html-table-striped bp3-html-table-bordered"
                style={{width: '95%'}}
                id="entry_history"
            >
                <thead>
                    <tr>
                        <th key="current">Current</th>
                        <th key="date">Date</th>
                        <th key="food">Food</th>
                        <th key="total">Total</th>
                        {renderCatagoryHeaders(catagories, keys)}
                    </tr>
                </thead>
                <tbody>
                    {_.map(entries, _.partial(renderEntry, keys))}
                </tbody>
            </table>
        </React.Fragment>
    );
};

export default EntryHistoryDisplay;
