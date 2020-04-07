import React from 'react';
import {useQuery, useMutation} from '@apollo/react-hooks';
import {Spinner} from '@blueprintjs/core';
import {ALL_CLIENTS, ADD_CLIENT} from '../../gql';
import ClientsDisplay from './ClientsDisplay';

const Clients = () => {
  const {loading, error, data, refetch} = useQuery(ALL_CLIENTS);
  const [addclientGQl, {d}] = useMutation(
      ADD_CLIENT,
      {
        onCompleted: refetch,
      });

  const addclient = (name, family) => addclientGQl({variables: {name, family}});

  if (loading) return <Spinner size="125"/>;
  if (error) return <p>Error :(</p>;
  return <ClientsDisplay {...data} addclient={addclient}/>;
};

export default Clients;
