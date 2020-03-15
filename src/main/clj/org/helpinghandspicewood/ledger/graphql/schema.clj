(ns org.helpinghandspicewood.ledger.graphql.schema
    (:require
        [clojure.java.io :as io]
        [com.walmartlabs.lacinia.util :as util]
        [com.walmartlabs.lacinia.schema :as schema]
        [clojure.edn :as edn]))

(defn resolver-map []
    {:user/current-user (fn [c a v]
        (hash-map
            :id "a9f05001-f609-45e5-b532-04cf75e8c8ae"
            :name "Brian Edwards"
            :email (list "brian@edwardstx.us")))})


(defn load-schema []
    (-> (io/resource "schema.edn")
        slurp
        edn/read-string
        (util/attach-resolvers (resolver-map))
        schema/compile
        ))