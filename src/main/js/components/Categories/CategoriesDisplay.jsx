import React, {useState} from 'react';
import _ from 'lodash';
import {Divider, Button, ControlGroup, InputGroup} from '@blueprintjs/core';

const CatagoryDescription =
    ({id, description, edit, setEdit, upsertCategory}) => {
      const [newDescription, setDescription] = useState(description);
      const update = () => upsertCategory(id, newDescription);
      if (edit) {
        return (
          <ControlGroup fill={true} vertical={false}>
            <InputGroup
              small={true}
              fill={true}
              value={newDescription}
              onChange={(e) => setDescription(e.target.value)}/>
            <Button icon="tick" intent="sucess" onClick={update} small={true}/>
            <Button
              small={true}
              icon="cross"
              intent="danger"
              onClick={() => setEdit(false)}/>
          </ControlGroup>
        );
      } else {
        return description;
      }
    };

const Catagory = ({category, upsertCategory}) => {
  const [edit, setEdit] = useState(false);

  const props = {...category, edit, setEdit, upsertCategory};
  return (
    <tr key={category.id}>
      <td>
        <CatagoryDescription {...props}/>
      </td>
      <td>
        <Button icon="edit" disabled={edit} minimal={true} small={true} intent="sucess" onClick={() => setEdit(true)}/>
        <Button icon="trash" minimal={true} intent="warning" small={true}/>
      </td>
    </tr>
  );
};

const CategoriesDisplay = ({categories, upsertCategory}) => {
  return (
    <React.Fragment>
      <h1>Categories</h1>
      <Divider />
      <table
        className="bp3-html-table bp3-interactive bp3-html-table-striped"
        style={{width: '95%'}}>
        <thead>
          <tr>
            <th>Catagory</th>
            <th style={{width: '6rem'}}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {_.chain(categories)
              .sortBy(['description'])
              .map((category) => <Catagory key={category.id} category={category} upsertCategory={upsertCategory}/>)
              .value()
          }
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default CategoriesDisplay;
