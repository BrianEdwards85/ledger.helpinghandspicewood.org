(ns org.helpinghandspicewood.ledger.db.permissions
    (:require
        [manifold.deferred :as d]
        [clojure.spec.alpha :as s]
        [org.helpinghandspicewood.ledger.spec :as specs]
        [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns] :as db]))

(def-db-fns "db/sql/permissions.sql")

(defn get-users-permissions [db user-id]
    {:pre [(s/valid? ::db/db db)
            (s/valid? ::specs/non-empty-string user-id)]}
    (d/future
        (get-users-permissions-sql (get-connection db) {:user_id user-id})))