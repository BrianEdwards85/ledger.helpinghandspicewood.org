(ns org.helpinghandspicewood.ledger
    (:require [config.core :refer [env]]
      [clojure.tools.logging :as log]
      [manifold.deferred :as d]
      [org.helpinghandspicewood.ledger.handler :refer [new-handler]]
      [org.helpinghandspicewood.ledger.server :refer [new-server]]
      [org.helpinghandspicewood.ledger.graphql :refer [new-graphql]]
      [org.helpinghandspicewood.ledger.keys :refer [new-keys]]
      [org.helpinghandspicewood.ledger.db :refer [new-db]]
      [com.stuartsierra.component :as component]
      )
  (:gen-class))

(defonce system (atom {}))

(defn init-system [env]
  (component/system-map
    :db (new-db "ledger" env)
    :keys (new-keys env)
    :graphql (new-graphql)
    :handler (new-handler)
    :server (new-server env)))

(defn -main [& args]
  (let [semaphore (d/deferred)]
    (reset! system (init-system env))

    (swap! system component/start)
    (log/info "Management UI booted")
    (deref semaphore)
    (log/info "Management going down")
    (component/stop @system)

    (shutdown-agents)
  ))

