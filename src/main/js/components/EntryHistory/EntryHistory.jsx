import React from 'react';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {Spinner} from '@blueprintjs/core';
import {GET_ENTRY_HISTORY} from '../../gql';
import EntryHistoryDispaly from './EntryHistoryDisplay';

const EntryHistory = () => {
    const variables = useParams();
    const {loading, error, data, refetch} = useQuery(GET_ENTRY_HISTORY, {variables});

    if (loading) return <Spinner size="125"/>;
    if (error) return <p>Error :(</p>;

    const {group_entries} = data;


    if (group_entries.length === 0) return <h1>Client not found</h1>;

    return <EntryHistoryDispaly {...data} />
};

export default EntryHistory;
