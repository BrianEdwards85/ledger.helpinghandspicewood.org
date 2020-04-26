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
  mutation AddClient($name: String!, $family: Int!){
    add_client(name: $name, family: $family)
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

export const REMOVE_CATEGORY = gql`
  mutation RemoveCategory($id: String!){
    remove_category(id: $id)
  }
`;

export const ADD_CLIENT_ENTRY = gql`
  mutation AddEntry(
    $categories: [EntryCategory],
    $client_id: String!,
    $effective_date: Date!,
    $food: Boolean!,
    $group_id: String,
    $notes: String
  ){
    add_entry(
      group_id: $group_id
      categories: $categories,
      client_id: $client_id,
      effective_date: $effective_date
      food: $food
      notes: $notes)
  }
`;

export const GET_CLIENTS = gql`
  query Client($ids: [String]){
    clients(ids: $ids){
      id
      name
      family
      added_on
      entries{
        id
        current
        group
        food
        effective_date
        entry_total
        notes
        values{
          value
          description
          category_id
        }
      }
    }
  }
`;

export const GET_ENTRY_HISTORY = gql`
  query EntryHistory($group: String!){
    group_entries(group: $group){
      added_by {
        id
        name
        email
      }
      added_on
      client {
        id
        name
      }
      current
      effective_date
      entry_total
      food
      group
      id
      notes
      values {
        category_id
        description
        value
      }
    }
  }
`;
