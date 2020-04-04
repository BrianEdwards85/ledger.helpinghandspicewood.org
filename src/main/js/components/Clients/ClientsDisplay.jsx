import React, {useState} from 'react';
import _ from 'lodash';
import {
  Divider,
  InputGroup,
  FormGroup,
  Button,
  ControlGroup,
  Alert,
} from '@blueprintjs/core';

const filterClients = (clients, filter) => {
  if (_.trim(filter).length == 0) {
    return clients;
  }
  const lowerFilter = filter.toLowerCase();
  return _.filter(clients, (client) => client.name.toLowerCase().includes(lowerFilter));
};

const ClientRow = ({client}) => (
  <tr key={client.id} onClick={() => console.log('ID: ' + client.id)}>
    <td>{client.name}</td>
    <td>4</td>
  </tr>
);

const ClientsDisplay = ({clients, addclient}) => {
  const [filter, setFilter] = useState('');
  const filteredClients = filterClients(clients, filter);
  const [isAlertOpen, setAlertOpen] = useState(false);
  return (
    <React.Fragment>
      <h1>Clients</h1>
      <Alert
        confirmButtonText="Create"
        cancelButtonText="Cancel"
        canEscapeKeyCancel={true}
        canOutsideClickCancel={true}
        isOpen={isAlertOpen}
        icon="insert"
        intent="success"
        onCancel={() => {
          setFilter('');
          setAlertOpen(false);
        }}
        onConfirm={() => addclient(filter)}
      >
        <p>Create new client {filter}?</p>
      </Alert>
      <FormGroup inline={true}>
        <ControlGroup>
          <InputGroup
            large={true}
            leftIcon="filter"
            placeholder="Filter"
            round={true}
            value={filter}
            onChange={(e) => setFilter(e.target.value)}/>
          <Divider/>
          <Button
            icon="insert"
            intent="primary"
            large={true}
            minimal={true}
            onClick={() => setAlertOpen(true)}
            disabled={!_.isEmpty(filteredClients)}/>
        </ControlGroup>
      </FormGroup>
      <Divider />
      <table className="bp3-html-table bp3-interactive bp3-html-table-striped" style={{width: '95%'}}>
        <thead>
          <tr>
            <th>Name</th>
            <th>Count</th>
          </tr>
        </thead>
        <tbody>
          {_.chain(filteredClients)
              .sortBy(['name'])
              .map((client) => <ClientRow key={client.id} client={client}/>)
              .value()
          }
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default ClientsDisplay;
