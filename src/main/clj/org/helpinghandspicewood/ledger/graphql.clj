(ns org.helpinghandspicewood.ledger.graphql
    (:require
        [org.helpinghandspicewood.ledger.graphql.schema :as schema]
        [com.stuartsierra.component :as component]
        [com.walmartlabs.lacinia :as lacinia]))

(defn q [{:keys [schema]} query]
    (lacinia/execute schema query nil nil))

(defrecord GraphQL [schema]
    component/Lifecycle

    (start [this]
        (assoc this :schema (schema/load-schema)))

    (stop [this]
        (assoc this :schema nil)))

(defn new-graphql []
    (map->GraphQL {}))