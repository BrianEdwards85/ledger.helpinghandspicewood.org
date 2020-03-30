-- :name get-all-clients-sql :? :*
SELECT clients.client_id as id, clients.name, clients.added_by, clients.added_on
FROM ledger.clients

-- :name add-client-sql :! :n
INSERT INTO ledger.clients(client_id, name, added_by)
VALUES (:client_id, :name, :added_by)