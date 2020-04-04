(ns org.helpinghandspicewood.ledger.orchestrator.entries
  (:require
    [manifold.deferred :as d]
    [org.helpinghandspicewood.ledger.db.entries :as entries-db]))

(defn check-permissions [permissions permission f]
  (if (some? (permissions permission))
    (f)
        (d/error-deferred (Exception. (str "User does not have " permission)))))

(defn get-client-entries
  ([db user client] (get-client-entries db user client false))
  ([db {:keys [permissions]} client archived]
  (check-permissions permissions "entries.list"
    #(entries-db/get-current-entries-by-client db client archived))))