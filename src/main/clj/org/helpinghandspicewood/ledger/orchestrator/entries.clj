(ns org.helpinghandspicewood.ledger.orchestrator.entries
  (:require
    [org.helpinghandspicewood.ledger.orchestrator.utils :refer [check-permissions]]
    [org.helpinghandspicewood.ledger.db.entries :as entries-db]))

(defn get-client-entries
  ([db user client] (get-client-entries db user client false))
  ([db {:keys [permissions]} client archived]
  (check-permissions permissions "entries.list"
    #(entries-db/get-current-entries-by-client db client archived))))