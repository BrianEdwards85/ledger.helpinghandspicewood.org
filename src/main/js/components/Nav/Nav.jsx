import React from 'react';
import {Button, Navbar, Alignment} from '@blueprintjs/core';
import {useHistory} from 'react-router-dom';

const Nav = ({user}) => {
  const history = useHistory();
  return (
    <Navbar fixedToTop={true}>
      <Navbar.Group align={Alignment.LEFT}>
        <Navbar.Heading>Helping Hands</Navbar.Heading>
        <Navbar.Divider />
        <Button
          className="bp3-minimal"
          text="Clients"
          onClick={() => history.push('/clients')} />
        <Button
          className="bp3-minimal"
          text="Categories"
          onClick={() => history.push('/categories')} />
        <Button className="bp3-minimal" text="Users" />
      </Navbar.Group>
      <Navbar.Group align={Alignment.RIGHT}>
        <Navbar.Divider />
        <Button className="bp3-minimal" text={user.name} />
      </Navbar.Group>
    </Navbar>
  );
};

export default Nav;
