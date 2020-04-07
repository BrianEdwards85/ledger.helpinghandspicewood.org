(ns org.helpinghandspicewood.ledger.db.entries
  (:require
    [manifold.deferred :as d]
    [clojure.spec.alpha :as s]
    [clojure.java.jdbc :as jdbc]
    [org.helpinghandspicewood.ledger.spec :as specs]
    [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant] :as db]))

(def-db-fns "db/sql/entries.sql")

(defn get-current-entries-by-clienty [db client archived]
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
        (merge
          {:notes nil}
          (assoc
            entry
            :effective_date
            (->
              entry
              :effective_date
              .toEpochMilli
              java.sql.Date.))))
      (set-current-entry-sql tx (select-keys entry [:entry_id]))
      (add-entry-categories tx
        {
          :entry_categories
          (map
            (fn [{:keys [category_id value]}]
              (vector (:entry_id entry) category_id value))
            (:categories entry))}))))

(defn create-entry [entry]
  (assoc
    (select-keys (first entry) [:group :added_by :client :group_total :effective_date :food :added_on :notes])
    :values
    (map #(select-keys % [:value :category_id :description]) (filter #(-> % :value some?) entry))))

(defn get-current-entries-by-client [db client x]
  {:pre [(s/valid? ::db/db db)]}
  (d/chain
    (d/future (get-current-entries-by-client-sqll (get-connection db) {:client_id client}))
    #(map timestamp->instant %)
    #(->> %
          (group-by :group)
          vals
          (map create-entry))))