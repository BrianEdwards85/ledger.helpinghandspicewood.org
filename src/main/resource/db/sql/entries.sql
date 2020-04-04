-- :name get-current-entries-by-client-sql :? :*
SELECT entries.group_id as group, entries.entry_id as id, entries.effective_date,
       entries.client_id as client, entries.food, entries.added_by,
       entries.added_on
FROM ledger.entries
WHERE entries.current
-- AND (:archived OR entries.removed_on is null)
AND entries.client_id = :client_id