-- :name get-categories-sql :? :*
SELECT categories.category_id as id, categories.description, categories.added_by, categories.added_on,
    categories.removed_by, categories.removed_on
FROM ledger.categories
WHERE :archived OR categories.removed_by IS NULL

-- :name upsert-category-sql :! :n
INSERT INTO ledger.categories (category_id, description, added_by)
VALUES (:id, :description, :added_by)
ON CONFLICT (category_id) DO UPDATE
SET description = :description, removed_by = NULL, removed_on = NULL