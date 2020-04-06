(ns org.helpinghandspicewood.ledger.orchestrator.entries
  (:require
    [manifold.deferred :as d]
    [org.helpinghandspicewood.ledger.orchestrator.utils :refer [check-permissions]]
    [org.helpinghandspicewood.ledger.uuid :refer [uuid]]
    [org.helpinghandspicewood.ledger.db.entries :as entries-db]))

(defn get-client-entries
  ([db user client] (get-client-entries db user client false))
  ([db {:keys [permissions]} client archived]
  (check-permissions permissions "entries.list"
    #(entries-db/get-current-entries-by-client db client archived))))

(defn add-entry [db {:keys [id permissions]} entry]
  (check-permissions permissions "entries.add"
    #(let [entry-id (uuid)]
      (d/chain
        (entries-db/add-entry
          db
          (merge
            {:group_id entry-id}
            entry
            {:entry_id entry-id :added_by id}))
        (constantly entry-id)))))
