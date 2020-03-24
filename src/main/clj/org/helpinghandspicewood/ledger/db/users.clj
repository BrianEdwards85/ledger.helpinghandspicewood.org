(ns org.helpinghandspicewood.ledger.db.users
    (:require
        [manifold.deferred :as d]
        [clojure.spec.alpha :as s]
        [org.helpinghandspicewood.ledger.spec :as specs]
        [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns] :as db]))

(def-db-fns "db/sql/users.sql")

(defn get-user-by-email [db email]
    {:pre [(s/valid? ::db/db db)
           (s/valid? ::specs/non-empty-string email)]}
    (d/future
        (get-user-by-email-sql (get-connection db) {:email email})))

(defn get-user-emails [db user-id]
    {:pre [(s/valid? ::db/db db)
           (s/valid? ::specs/non-empty-string user-id)]}
    (d/future
        (get-user-emails-sql (get-connection db) {:user_id user-id})))

(defn get-user-permissions [db email]
    {:pre [(s/valid? ::db/db db)
           (s/valid? ::specs/non-empty-string email)]}
    (d/future
        (set
            (map :permission_id
                (get-user-permissions-by-email-sql
                    (get-connection db)
                     {:email email})))))
