(ns org.helpinghandspicewood.ledger.db.categories
  (:require
    [manifold.deferred :as d]
    [clojure.spec.alpha :as s]
    [org.helpinghandspicewood.ledger.spec :as specs]
    [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant] :as db]))

(def-db-fns "db/sql/categories.sql")

(defn get-categories [db archived]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
    (map
      timestamp->instant
      (get-categories-sql (get-connection db) {:archived archived}))))

(defn upsert-category [db category]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
    (upsert-category-sql (get-connection db) category)))

(defn archive-category [db category-id user-id]
  {:pre [(s/valid? ::db/db db)]}
  (d/future
    (archive-category-sql
      (get-connection db)
      {:category_id category-id :removed_by user-id})))
