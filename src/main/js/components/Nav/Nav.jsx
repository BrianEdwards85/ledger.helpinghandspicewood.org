import React from 'react';
import {Button, Navbar, Alignment} from '@blueprintjs/core';
import {useHistory} from 'react-router-dom';

const Nav = ({user}) => {
  const history = useHistory();
  const handleClick = () => {
    history.push('/clients');
  };
  return (
    <Navbar fixedToTop={true}>
      <Navbar.Group align={Alignment.LEFT}>
        <Navbar.Heading>Helping Hands</Navbar.Heading>
        <Navbar.Divider />
        <Button className="bp3-minimal" icon="home" text="Home" onClick={handleClick} />
        <Button className="bp3-minimal" icon="document" text="Files" />
      </Navbar.Group>
      <Navbar.Group align={Alignment.RIGHT}>
        <Navbar.Divider />
        <Button className="bp3-minimal" text={user.name} />
      </Navbar.Group>
    </Navbar>
  );
};

export default Nav;
