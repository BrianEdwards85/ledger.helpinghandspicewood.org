(ns org.helpinghandspicewood.ledger.db
    (:require [org.helpinghandspicewood.ledger.spec :as specs]
              [hikari-cp.core :refer :all]
              [hugsql.core :as hugsql]
              [hugsql.adapter.clojure-java-jdbc :as adapter]
              [clojure.spec.alpha :as s]
              [clojure.tools.logging :as log]
              [com.stuartsierra.component :as component])
    (:import [org.flywaydb.core
              Flyway]))

(defprotocol DBConnection
    (get-connection [this]))

(s/def ::db #(satisfies? DBConnection %))
(s/def ::db-url ::specs/non-empty-string)
(s/def ::db-user ::specs/non-empty-string)
(s/def ::db-password ::specs/non-empty-string)
(s/def ::db-pool-size ::specs/positive-int)
(s/def ::db-env (s/keys :req-un [::db-url ::db-user ::db-password] :opt-un [::db-pool-size]))

(defrecord Database [schema db-conf datasource]
    component/Lifecycle

    (start [this]
        (do
            (log/info "Connecting to DB")
            (let [ds (make-datasource db-conf)]
            (log/info "Connected to DB, starting migration")
            (-> (Flyway/configure)
                (.schemas (into-array [schema]))
                (.defaultSchema schema)
                (.dataSource ds)
                (.load)
                (.migrate))
            (log/info "Migration complete")
            (assoc this :datasource ds))))

    (stop [this]
        (close-datasource datasource)
        (assoc this :datasource nil))

    DBConnection

    (get-connection [this] (select-keys this [:datasource]))
)

(defn new-db [schema {:keys [db-url db-user db-password db-pool-size] :as env}]
    {:pre [(s/valid? ::db-env env)
           (s/valid? ::specs/non-empty-string schema)]}
    (map->Database {:schema schema :db-conf {:username db-user :password db-password :jdbc-url db-url :minimum-idle (or db-pool-size 3)}}))

(defn def-db-fns [f]
    (hugsql/def-db-fns f {:adapter (adapter/hugsql-adapter-clojure-java-jdbc)}))

(defn timestamp->instant [e]
    (apply
        hash-map
        (mapcat
            (fn [[k v]]
                (if (instance? java.sql.Timestamp v)
                    [k (.toInstant v)]
                    [k v]))
            e)))