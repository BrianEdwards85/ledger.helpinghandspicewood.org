(ns org.helpinghandspicewood.ledger.orchestrator.clients
    (:require
        [manifold.deferred :as d]
        [org.helpinghandspicewood.ledger.db.clients :as clients-db]))

(defn get-all-clients [db {:keys [permissions]}]
    (if (some? (permissions "client.list"))
        (clients-db/get-all-clients db)
        (d/error-deferred (Exception. "User does not have permission to get clients"))))

(defn add-client [db {:keys [id permissions]} client]
    (if (some? (permissions "client.add"))
        (clients-db/add-client db (assoc client :added_by id))
        (d/error-deferred (Exception. "User does not have permission to add client"))))
