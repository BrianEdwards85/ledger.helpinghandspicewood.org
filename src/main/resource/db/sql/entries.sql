-- :name get-current-entries-by-client-sql :? :*
SELECT entries.group_id as group, entries.entry_id as id, entries.effective_date,
       entries.client_id as client, entries.food, entries.added_by,
       entries.added_on
FROM ledger.entries
WHERE entries.current
-- AND (:archived OR entries.removed_on is null)
AND entries.client_id = :client_id

-- :name add-entry-sql :! :n
INSERT INTO ledger.entries (entry_id, group_id, effective_date, client_id, current, added_by, food)
VALUES (:entry_id, :group_id, :effective_date, :client_id, false, :added_by, :food)

-- :name set-current-entry-sql :! :n
UPDATE ledger.entries
SET current = (entry_id = :entry_id)
WHERE group_id IN (SELECT group_id FROM ledger.entries WHERE entry_id = :entry_id)

-- :name add-entry-categories :! :n
INSERT INTO ledger.entry_categories (entry_id, category_id, value)
VALUES :tuple*:entry_categories