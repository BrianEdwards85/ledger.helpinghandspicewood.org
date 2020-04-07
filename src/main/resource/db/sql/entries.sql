-- :name get-current-entries-by-client-sql :? :*
SELECT entries.group_id as group, entries.entry_id as id, entries.effective_date,
       entries.client_id as client, entries.food, entries.added_by,
       entries.added_on, entries.notes
FROM ledger.entries
WHERE entries.current
-- AND (:archived OR entries.removed_on is null)
AND entries.client_id = :client_id

-- :name add-entry-sql :! :n
INSERT INTO ledger.entries (entry_id, group_id, effective_date, client_id, current, added_by, food, notes)
VALUES (:entry_id, :group_id, :effective_date, :client_id, false, :added_by, :food, :notes)

-- :name set-current-entry-sql :! :n
UPDATE ledger.entries
SET current = (entry_id = :entry_id)
WHERE group_id IN (SELECT group_id FROM ledger.entries WHERE entry_id = :entry_id)

-- :name add-entry-categories :! :n
INSERT INTO ledger.entry_categories (entry_id, category_id, value)
VALUES :tuple*:entry_categories

-- :name get-current-entries-by-client-sqll :? :*
SELECT entries.group_id as group, entries.effective_date, entries.client_id as client,
       entries.added_on, entries.added_by, entries.food, entries.notes,
       entry_categories.category_id, categories.description, entry_categories.value,
       SUM(entry_categories.value) OVER (PARTITION BY group_id) as group_total
FROM ledger.entries
LEFT JOIN ledger.entry_categories on entries.entry_id = entry_categories.entry_id
LEFT JOIN ledger.categories on entry_categories.category_id = categories.category_id
WHERE entries.current AND entries.client_id = :client_id