import React from 'react';
import {useParams} from 'react-router-dom';
import {useQuery, useMutation} from '@apollo/react-hooks';
import {Spinner} from '@blueprintjs/core';
import {GET_CLIENTS} from '../../gql';
import ClientDisplay from './ClientDisplay';

const Client = () => {
  const {client} = useParams();
  const variables = {ids: [client]};
  const {loading, error, data, refetch} = useQuery(GET_CLIENTS, {variables});

  if (loading) return <Spinner size="125"/>;
  if (error) return <p>Error :(</p>;

  const {clients} = data;

  if (clients.length === 0) return <h1>Client not found</h1>;

  return <ClientDisplay client={clients[0]} />;
};


export default Client;
