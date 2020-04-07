import React from 'react';
import {useQuery, useMutation} from '@apollo/react-hooks';
import {Spinner} from '@blueprintjs/core';
import {ALL_CATEGORIES, UPSERT_CATEGORIES, REMOVE_CATEGORY} from '../../gql';
import CategoriesDisplay from './CategoriesDisplay';

const Categories = () => {
  const {loading, error, data, refetch} = useQuery(ALL_CATEGORIES);
  const [upsertCategoryGql, {d}] = useMutation(
      UPSERT_CATEGORIES,
      {onCompleted: refetch});
  const [removeCategoryGql, {e}] = useMutation(
      REMOVE_CATEGORY,
      {onCompleted: refetch});

  const upsertCategory = (id, description) =>
    upsertCategoryGql({variables: {id, description}});

  const removeCategory = (id) =>
    removeCategoryGql({variables: {id}});

  if (loading) return <Spinner size="125"/>;
  if (error) return <p>Error :(</p>;

  const props = {
    ...data,
    upsertCategory,
    removeCategory,
  };

  return <CategoriesDisplay {...props}/>;
};

export default Categories;
