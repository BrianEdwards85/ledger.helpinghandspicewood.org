(ns org.helpinghandspicewood.ledger.orchestrator.clients
    (:require
        [manifold.deferred :as d]
        [org.helpinghandspicewood.ledger.db.clients :as clients-db]))

(defn get-clients [db {:keys [permissions]} ids]
    (if (some? (permissions "client.list"))
        (if (empty? ids)
            (clients-db/get-all-clients db)
            (clients-db/get-clients db ids))
        (d/error-deferred (Exception. "User does not have permission to get clients"))))

(defn add-client [db {:keys [id permissions]} client]
    (if (some? (permissions "client.add"))
        (clients-db/add-client db (assoc client :added_by id))
        (d/error-deferred (Exception. "User does not have permission to add client"))))
