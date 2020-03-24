(ns org.helpinghandspicewood.ledger.graphql
    (:require
        [org.helpinghandspicewood.ledger.graphql.schema :as schema]
        [com.stuartsierra.component :as component]
        [com.walmartlabs.lacinia :as lacinia]))

(defn q [{:keys [schema]} {:keys [query variables]} ctx]
    (lacinia/execute schema query variables (select-keys ctx [:user :permissions])))

(defrecord GraphQL [schema db]
    component/Lifecycle

    (start [this]
        (assoc this :schema (schema/load-schema this)))

    (stop [this]
        (assoc this :schema nil)))

(defn new-graphql []
    (component/using
        (map->GraphQL {})
        [:db]))