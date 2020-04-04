(ns org.helpinghandspicewood.ledger.db.entries
  (:require
    [manifold.deferred :as d]
    [clojure.spec.alpha :as s]
    [org.helpinghandspicewood.ledger.spec :as specs]
    [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant] :as db]))

(def-db-fns "db/sql/entries.sql")

(defn get-current-entries-by-client [db client archived]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
    (map
      timestamp->instant
      (get-current-entries-by-client-sql (get-connection db) {:client_id client} ))))