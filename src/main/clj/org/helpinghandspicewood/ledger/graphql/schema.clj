(ns org.helpinghandspicewood.ledger.graphql.schema
  (:require
    [clojure.java.io :as io]
    [manifold.deferred :as d]
    [org.helpinghandspicewood.ledger.db.users :as users]
    [org.helpinghandspicewood.ledger.orchestrator.clients :as clients]
    [org.helpinghandspicewood.ledger.orchestrator.entries :as entries]
    [org.helpinghandspicewood.ledger.orchestrator.categories :as categories]
    [com.walmartlabs.lacinia.util :as util]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia.resolve :as resolve]
    [clojure.edn :as edn])
  (:import
    java.time.format.DateTimeFormatter
    java.time.Instant)
)

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

(defn get-error-message [e]
  (loop [e e]
    (cond
      (nil? e) {:message "Unknown error"}
      (-> e :data string?) (:data e)
      (:cause e) (recur (:cause e))
      :else {:message "Unknown error"} )))

(defn resolve [system resolver]
  (fn [context variables value]
    (let [result (resolve/resolve-promise)]
      (d/on-realized
          (resolver (merge system context {:variables variables :value value}))
          #(resolve/deliver! result %)
          #(resolve/deliver! result nil {:message (str %)}))
        result)))

(def PARSER java.time.format.DateTimeFormatter/ISO_INSTANT)
(defn parse-date [s]
  (when
    (string? s)
    (try
      (java.time.Instant/from
        (.parse PARSER s))
      (catch Throwable _ nil))))

(def scalar-map {
  :date/parse parse-date
  :date/serialize str
})

(defn client-for-entry [{:keys [db user value]}]
  (d/chain
    (clients/add-client db user (list (:client value)))
    first))

(defn resolver-map [system]
  (let [resolve (partial resolve system)]
  { :user/current current-user
    :user/emails (partial user-emails system)
    :user/permissions (partial user-permissions system)
    :user/added_by (resolve (fn [{:keys [db value]}] (users/get-user-by-id db (:added_by value))))
    :client/all (resolve (fn [{:keys [db user variables]}] (clients/get-clients db user (:ids variables))))
    :client/add (resolve (fn [{:keys [db user variables]}] (clients/add-client db user variables)))
    :client/for-entry (resolve client-for-entry)
    :entry/for-client (resolve (fn [{:keys [db user value]}] (entries/get-client-entries db user (:id value))))
    :category/get (resolve (fn [{:keys [db user variables]}] (categories/get-categories db user (-> variables :archived true?))))
    :category/upsert (resolve (fn [{:keys [db user variables]}] (categories/upsert-category db user variables)))
    :category/remove (resolve (fn [{:keys [db user variables]}] (categories/archive-category db user (:id variables))))
    :entry/add (resolve (fn [{:keys [db user variables]}] (entries/add-entry db user variables)))
    }))


(defn load-schema [system]
  (-> (io/resource "schema.edn")
    slurp
    edn/read-string
    (util/attach-resolvers (resolver-map system))
    (util/attach-scalar-transformers scalar-map)
    schema/compile))