(ns org.helpinghandspicewood.ledger.db.entries
  (:require
    [manifold.deferred :as d]
    [clojure.spec.alpha :as s]
    [clojure.java.jdbc :as jdbc]
    [org.helpinghandspicewood.ledger.spec :as specs]
    [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant bigdec->int] :as db]))

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
    (select-keys (first entry) [:id :current :group :added_by :client :entry_total :effective_date :food :added_on :notes])
    :values
    (map #(select-keys % [:value :category_id :description]) (filter #(-> % :value some?) entry))))

(defn group-entries [client-entries]
  (->> client-entries
    (group-by :id)
    vals
    (map create-entry)))

(defn get-entries [db paramaters]
  {:pre [(s/valid? ::db/db db)]}
  (d/chain
    (d/future (get-entries-sql (get-connection db) paramaters))
    #(map timestamp->instant %)
    #(map bigdec->int %)
    group-entries))

(defn get-current-entries-by-client [db client]
  (get-entries db {:client_id client :group_id nil}))

(defn get-entries-by-group [db group]
  (get-entries db {:client_id nil :group_id group}))
