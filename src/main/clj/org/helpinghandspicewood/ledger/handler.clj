(ns org.helpinghandspicewood.ledger.handler
  (:require [com.stuartsierra.component :as component]
            [yada.yada :as yada]
            [yada.resources.resources-resource :refer [new-resources-resource]]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]
            [org.helpinghandspicewood.ledger.graphql :refer [q]]
            [org.helpinghandspicewood.ledger.index :refer [index]]
            [org.helpinghandspicewood.ledger.authentication :as auth]))

(defn build-routes [{:keys [graphql keys] :as system}]
  (let [authorize #(auth/authorized system % auth/redirect)]
    ["" [["/assets/" (new-classpath-resource "public")]
         ["/hello" (yada/resource {:methods {:get {:produces #{"text/html"}
                                                   :response "Hello Wolrd"}}})]
         ["/cb" (yada/resource {:methods {:get {:parameters {:query {:code String
                                                                     :state String}}
                                                :produces #{"text/html"}
                                                :response auth/callback}}})]
         ["/graphql" (yada/resource {:methods {:post {:produces #{"application/json"}
                                                      :consumes "application/json"
                                                      :response (authorize (fn [{:keys [body] :as ctx}]
                                                                  (q graphql body ctx)))}}})]
         ["/jwt" (yada/resource {:methods {:get {:produces #{"application/json"}
                                                 :response (authorize #(select-keys % [:token :user :permissions]))}}})]
         [true (yada/resource {:methods {:get {:produces #{"text/html"}
                                               :response (authorize index)}}})]]]))

(defrecord Handler [routes graphql keys db]
  component/Lifecycle

  (start [this]
    (assoc this :routes (build-routes this)))

  (stop [this]
    (assoc this :routes nil)))

(defn new-handler []
  (component/using
   (map->Handler {})
   [:graphql :keys :db]))