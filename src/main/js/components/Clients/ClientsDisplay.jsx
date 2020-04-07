import React, {useState} from 'react';
import _ from 'lodash';
import {
  Divider,
  InputGroup,
  FormGroup,
  Button,
  ControlGroup,
  Alert,
  NumericInput,
} from '@blueprintjs/core';

const filterClients = (clients, filter) => {
  if (_.trim(filter).length == 0) {
    return clients;
  }
  const lowerFilter = filter.toLowerCase();
  return _.filter(clients, (client) =>
    client.name.toLowerCase().includes(lowerFilter));
};

const ClientRow = ({client}) => (
  <tr key={client.id} onClick={() => console.log('ID: ' + client.id)}>
    <td>{client.name}</td>
    <td>{client.family}</td>
  </tr>
);

const CreateClient = ({name, onCancel, addClient, isAlertOpen}) => {
  const [family, setFamily] = useState(1);
  return (
    <Alert
      confirmButtonText="Create"
      cancelButtonText="Cancel"
      canEscapeKeyCancel={true}
      canOutsideClickCancel={true}
      isOpen={isAlertOpen}
      icon="insert"
      intent="success"
      onCancel={onCancel}
      onConfirm={() => addClient(name, family)}
    >
      <p>Create new client {name}?</p>
      <NumericInput
        min={1}
        selectAllOnFocus={true}
        placeholder="Family"
        large={true}
        value={family}
        onValueChange={(num) => setFamily(num)}/>
    </Alert>
  );
};

const ClientsDisplay = ({clients, addclient}) => {
  const [filter, setFilter] = useState('');
  const filteredClients = filterClients(clients, filter);
  const [isAlertOpen, setAlertOpen] = useState(false);
  const onCancel = () => {
    setAlertOpen(false);
    setFilter('');
  };
  return (
    <React.Fragment>
      <h1>Clients</h1>
      <CreateClient
        name={filter}
        onCancel={onCancel}
        addClient={addclient}
        isAlertOpen={isAlertOpen}
      />
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
            disabled={!_.isEmpty(filteredClients) && filter.length > 0}/>
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
