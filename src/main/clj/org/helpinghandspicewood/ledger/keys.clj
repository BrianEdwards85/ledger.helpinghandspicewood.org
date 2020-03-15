(ns org.helpinghandspicewood.ledger.keys
    (:require
        [com.stuartsierra.component :as component]
        [cheshire.core :as json]
        [buddy.core.keys :as keys]
        [byte-streams :as bs]
        [aleph.http :as http]))

(defrecord Keys [keys url]
    component/Lifecycle

    (start [this]
        (->> (-> @(http/get url)
                :body
                bs/to-reader
                (json/parse-stream true)
                :keys)
            (map keys/jwk->public-key)
            (assoc this :keys)))

    (stop [this]
        (assoc this :keys nil)))

(defn new-keys [{:keys [jwks-url]}]
    (map->Keys {:url jwks-url}))