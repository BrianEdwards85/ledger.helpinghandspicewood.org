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
        family
      }
  }
`;

export const ADD_CLIENT = gql`
  mutation AddClient($name: String!){
    add_client(name: $name)
  }
`;

export const ALL_CATEGORIES = gql`
  {
    categories{
      description
      id
      removed_on
    }
  }
`;

export const UPSERT_CATEGORIES = gql`
  mutation UpsertCategory($id: String!, $description: String!){
    upsert_category(description: $description, id: $id)
  }
`;
