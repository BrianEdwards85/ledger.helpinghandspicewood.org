(ns org.helpinghandspicewood.ledger.orchestrator.clients
    (:require
        [org.helpinghandspicewood.ledger.db.clients :as clients-db]))

(defn get-all-clients [db user]
    (clients-db/get-all-clients db))