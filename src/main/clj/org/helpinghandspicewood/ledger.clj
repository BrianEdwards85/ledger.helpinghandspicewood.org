(ns org.helpinghandspicewood.ledger
    (:require [config.core :refer [env]]
      [clojure.tools.logging :as log]
      [com.stuartsierra.component :as component]
      [manifold.deferred :as d]
      [org.helpinghandspicewood.ledger.handler :refer [new-handler]]
      [org.helpinghandspicewood.ledger.server :refer [new-server]])
  (:gen-class))

(defonce system (atom {}))

(defn init-system [env]
  (component/system-map
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

