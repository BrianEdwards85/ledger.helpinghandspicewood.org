-- :name get-all-clients-sql :? :*
SELECT clients.client_id as id, clients.name, clients.family, clients.added_by, clients.added_on
FROM ledger.clients

-- :name get-clients-sql :? :*
SELECT clients.client_id as id, clients.name, clients.family, clients.added_by, clients.added_on
FROM ledger.clients
WHERE clients.client_id in (:v*:ids)

-- :name add-client-sql :! :n
INSERT INTO ledger.clients(client_id, name, family, added_by)
VALUES (:client_id, :name, :family, :added_by)