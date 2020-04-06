(ns org.helpinghandspicewood.ledger.db.entries
  (:require
    [manifold.deferred :as d]
    [clojure.spec.alpha :as s]
    [clojure.java.jdbc :as jdbc]
    [org.helpinghandspicewood.ledger.spec :as specs]
    [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant] :as db]))

(def-db-fns "db/sql/entries.sql")

(defn get-current-entries-by-client [db client archived]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
    (map
      timestamp->instant
      (get-current-entries-by-client-sql (get-connection db) {:client_id client} ))))

(defn add-entry [db entry]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
  (jdbc/with-db-transaction [tx (get-connection db)]
    (add-entry-sql tx
      (assoc
        entry
        :effective_date
        (->
          entry
          :effective_date
          .toEpochMilli
          java.sql.Date.)))
    (set-current-entry-sql tx (select-keys entry [:entry_id]))
    (add-entry-categories tx
      {
        :entry_categories
        (map
          (fn [{:keys [category_id value]}]
            (vector (:entry_id entry) category_id value))
          (:categories entry))}))))
