(ns org.helpinghandspicewood.ledger.db.clients
    (:require
        [manifold.deferred :as d]
        [clojure.spec.alpha :as s]
        [org.helpinghandspicewood.ledger.uuid :refer [uuid]]
        [org.helpinghandspicewood.ledger.spec :as specs]
        [org.helpinghandspicewood.ledger.db :refer [get-connection def-db-fns timestamp->instant] :as db]))


(s/def ::id ::specs/non-empty-string)
(s/def ::name ::specs/non-empty-string)
(s/def ::added_by ::specs/non-empty-string)
(s/def ::family ::specs/positive-int)
(s/def ::client (s/keys :req-un [::name ::added_by ::family]))

(def-db-fns "db/sql/clients.sql")

(defn get-clients [db ids]
    {:pre [(s/valid? ::db/db db)]}
    (d/future
        (map
            timestamp->instant
            (get-clients-sql (get-connection db) {:ids ids}))))

(defn get-all-clients [db]
    {:pre [(s/valid? ::db/db db)]}
    (d/future
        (map
            timestamp->instant
            (get-all-clients-sql (get-connection db)))))

(defn add-client [db client]
    {:pre [(s/valid? ::db/db db)
           (s/valid? ::client client)]}
    (let [id (uuid)]
        (d/chain
            (d/future (add-client-sql (get-connection db) (assoc client :client_id id)))
            (fn [& args] id))))
