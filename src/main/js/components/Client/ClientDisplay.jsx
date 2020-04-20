/* eslint-disable camelcase */
import React, {useState} from 'react';
import _ from 'lodash';

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

const ClientDisplay = ({client}) => {
  const {name, family} = client;

  const entries = _.chain(client.entries)
      .map(augumentEntry)
      .sortBy(['timestamp'])
      .value();
  const catagories = getCatagories(entries);

  return (
    <React.Fragment>
      <h1>Client: {name}</h1>
      <div>Family: {family}</div>
      <h2>Visits</h2>
      <table className="bp3-html-table bp3-interactive bp3-html-table-striped" style={{width: '95%'}}>
        <thead>
          <tr>
            <th key="date">Date</th>
            <th key="total">Total</th>
            {_.chain(catagories)
                .keys()
                .sort()
                .map((catagory) => <th key={catagory}>{catagories[catagory]}</th>)
                .value()}
          </tr>
        </thead>
        <tbody>
          {_.map(entries, (entry) => (
            <tr key={entry.id}>
              <td key={`${entry.id}_date`}>{entry.effective_date}</td>
              <td key={`${entry.id}_total`}>{entry.entry_total}</td>
              {_.chain(catagories)
                  .keys()
                  .sort()
                  .map((category) =>
                    <td key={`${entry.id}_${category}`}>
                      {category in entry.value_map ?
                        entry.value_map[category] : '-'}
                    </td>)
                  .value()
              }
            </tr>
          ))}
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default ClientDisplay;
