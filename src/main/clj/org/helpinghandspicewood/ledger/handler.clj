(ns org.helpinghandspicewood.ledger.handler
  (:require [com.stuartsierra.component :as component]
    [yada.yada :as yada]
    [yada.resources.classpath-resource :refer [new-classpath-resource]]
  ))

(defn build-routes []
  ["" [
    ["/assets/" (new-classpath-resource "public") ]
    [ true (yada/resource {:methods {
      :get {
        :produces #{"text/html"}
        :response "Hello Wolrd"
      }
    }})]
  ]])

(defrecord Handler [routes]
  component/Lifecycle

  (start [this]
    (assoc this :routes (build-routes)))

  (stop [this]
    (assoc this :routes nil)))

(defn new-handler []
  (map->Handler {}))