import {gql} from 'apollo-boost';

export const CURRENT_USER = gql`
  {
    current_user{
        email
        id
        name
        permissions
      }
  }
`;

export const ALL_CLIENTS = gql`
  {
    clients{
        id
        name
      }
  }
`;

export const ADD_CLIENT = gql`
  mutation AddClient($name: String!){
    add_client(name: $name)
  }
`;
