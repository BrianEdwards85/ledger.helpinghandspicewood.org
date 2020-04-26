import React, {useState} from 'react';


const EntryHistoryDisplay = ({group_entries}) => {
    return <h1>{`History: ${group_entries[0].group}`}</h1>;
};

export default EntryHistoryDisplay;
