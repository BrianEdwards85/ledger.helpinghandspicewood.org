(ns org.helpinghandspicewood.ledger.server
  (:require [yada.yada :as yada]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]))

(defrecord Server [handler port server]
  component/Lifecycle

  (start [this]
    (let [port (or (:port this) 8080)
          routes (-> handler :routes)
          s (yada/listener routes {:port port})]
      (do
        (log/info (str "Started server listening on port " port))
        (assoc this :server s))
      ))

  (stop [this]
    ((:close server))
    (log/warn "Stopped server")
    (assoc this :server nil)))


(defn new-server [conf]
  (component/using
    (map->Server (select-keys conf [:port]))
    [:handler]))