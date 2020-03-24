(ns org.helpinghandspicewood.ledger.graphql.schema
    (:require
        [clojure.java.io :as io]
        [manifold.deferred :as d]
        [org.helpinghandspicewood.ledger.db.users :as users]
        [com.walmartlabs.lacinia.util :as util]
        [com.walmartlabs.lacinia.schema :as schema]
        [com.walmartlabs.lacinia.resolve :as resolve]
        [clojure.edn :as edn]))

(defn current-user [context variables value]
    (:user context))

(defn user-emails [{:keys [db]} context variables {:keys [id]}]
    (let [result (resolve/resolve-promise)]
        (d/on-realized
            (users/get-user-emails db id)
            #(resolve/deliver! result (map :email %))
            #(resolve/deliver! result nil %))
        result))

(defn user-permissions [{:keys [db]} context variables {:keys [email]}]
    (let [result (resolve/resolve-promise)]
        (d/on-realized
            (users/get-user-permissions db email)
            #(resolve/deliver! result %)
            #(resolve/deliver! result nil %))
        result))

(defn resolver-map [system]
    {
        :user/current current-user
        :user/emails (partial user-emails system)
        :user/permissions (partial user-permissions system)
        })


(defn load-schema [system]
    (-> (io/resource "schema.edn")
        slurp
        edn/read-string
        (util/attach-resolvers (resolver-map system))
        schema/compile))