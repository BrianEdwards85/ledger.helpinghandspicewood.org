(ns org.helpinghandspicewood.ledger.db.clients
    (:require
        [manifold.deferred :as d]
        [clojure.spec.alpha :as s]
        [org.helpinghandspicewood.ledger.spec :as specs]
        [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns] :as db]))


(s/def ::id ::specs/non-empty-string)
(s/def ::name ::specs/non-empty-string)
(s/def ::added_by ::specs/non-empty-string)
(s/def ::client (s/keys :req-un [::id ::name ::added_by]))

(def-db-fns "db/sql/users.sql")

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-clients [db]
    {:pre [(s/valid? ::db/db db)]}
    (d/future
        (get-all-clients-sql (get-connection db))))

(defn add-client [db client]
    {:pre [(s/valid? ::db/db db)
           (s/valid? ::client client)]}
    (let [id (uuid)]
        (d/chain
            (d/future (add-client-sql (get-connection db) (assoc client :id id)))
            (fn [& args] id))))
