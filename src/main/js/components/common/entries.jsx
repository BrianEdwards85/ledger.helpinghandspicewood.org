import _ from 'lodash';
import React from 'react';

const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
});

export const formatCurrency = (x) => formatter.format(x / 100);

export const augumentEntry = (entry) => {
    const {added_on, effective_date, values} = entry;
    const timestamp = Date.parse(effective_date);
    const added_timestamp = Date.parse(added_on);
    const value_map = _.chain(values)
        .groupBy('category_id')
        .mapValues(_.head)
        .mapValues('value')
        .value();

    return {
        ...entry,
        timestamp,
        value_map,
        added_timestamp,
    };
};

export const getCatagories = (entries) => _.chain(entries)
    .flatMap('values')
    .groupBy('category_id')
    .mapValues(_.head)
    .mapValues('description')
    .value();

export const catagoryKeys = (catagories) => _.chain(catagories)
    .keys()
    .sort()
    .value();

export const getCatagoriesAndKeys = (entries) => {
    const catagories = getCatagories(entries);
    const keys = catagoryKeys(catagories);

    return {catagories, keys};
};

export const renderCatagoryHeaders = (catagories, keys) =>
    _.map(keys, (catagory) =>
        <th key={catagory}>
            {catagories[catagory]}
        </th>);

export const renderEntryValues = (keys, entry) =>
    _.map(keys, (catagory) =>
        <td key={`${entry.id}_${catagory}`}>
            {catagory in entry.value_map ?
                formatCurrency(entry.value_map[catagory]) : '-'}
        </td>);
