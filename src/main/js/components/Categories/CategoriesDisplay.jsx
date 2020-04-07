import React, {useState} from 'react';
import _ from 'lodash';
import {
  Divider,
  Button,
  ControlGroup,
  InputGroup,
  Alert,
  Label,
} from '@blueprintjs/core';
import './Categories.scss';

const CategoryDescription =
    ({id, description, edit, setEdit, upsertCategory}) => {
      const [newDescription, setDescription] = useState(description);
      const update = () => {
        upsertCategory(id, newDescription);
        setEdit(false);
      };
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

const NOT_LETTER_REGEX = /[^A-Z]/gi;
const WHITESPACE_REGEX = /(\ )+/g;
const AddCategory = ({addCatagory, isAddCatagoryOpen, onCancel}) => {
  const [name, setName] = useState('');
  const add = () => {
    const id = name
        .toUpperCase()
        .replace(NOT_LETTER_REGEX, ' ')
        .trim()
        .replace(WHITESPACE_REGEX, '_');
    addCatagory(id, name);
  };
  return (
    <Alert
      confirmButtonText="Create"
      cancelButtonText="Cancel"
      canEscapeKeyCancel={true}
      canOutsideClickCancel={true}
      isOpen={isAddCatagoryOpen}
      onOpened={() => setName('')}
      icon="insert"
      intent="success"
      onCancel={onCancel}
      onConfirm={add}
    >
      <h2>{'Add category'}</h2>
      <Label className="bp3-inline">
        {'Name:'}
        <InputGroup
          small={true}
          value={name}
          onChange={(e) => setName(e.target.value)}/>
      </Label>
    </Alert>
  );
};

const Category = ({category, upsertCategory}) => {
  const [edit, setEdit] = useState(false);

  const props = {...category, edit, setEdit, upsertCategory};
  return (
    <tr key={category.id}>
      <td>
        <CategoryDescription {...props}/>
      </td>
      <td>
        <Button icon="edit" disabled={edit} minimal={true} small={true} intent="sucess" onClick={() => setEdit(true)}/>
        <Button icon="trash" minimal={true} intent="warning" small={true}/>
      </td>
    </tr>
  );
};

const CategoriesDisplay = ({categories, upsertCategory}) => {
  const [isAddCatagoryOpen, setAddCatagoryOpen] = useState(false);
  return (
    <React.Fragment>
      <div id="categories-display-headder">
        <h1>Categories</h1>
        <Button
          icon="add"
          outlined={true}
          large={true}
          intent="primary"
          onClick={() => setAddCatagoryOpen(true)}
        />
      </div>
      <AddCategory
        addCatagory={upsertCategory}
        isAddCatagoryOpen={isAddCatagoryOpen}
        onCancel={() => setAddCatagoryOpen(false)}
      />
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
              .map((category) =>
                <Category
                  key={category.id}
                  category={category}
                  upsertCategory={upsertCategory}
                />)
              .value()
          }
        </tbody>
      </table>
    </React.Fragment>
  );
};

export default CategoriesDisplay;
