(ns org.helpinghandspicewood.ledger.orchestrator.categories
  (:require
    [org.helpinghandspicewood.ledger.orchestrator.utils :refer [check-permissions]]
    [org.helpinghandspicewood.ledger.db.categories :as categories-db]))

(defn get-categories [db {:keys [permissions]} archived]
  (check-permissions permissions
                     "categories.list"
                     #(categories-db/get-categories db archived)))

(defn upsert-category [db {:keys [id permissions]} category]
  (check-permissions permissions
                     "categories.add"
                     #(categories-db/upsert-category db (assoc category :added_by id))))

(defn archive-category [db {:keys [id permissions]} category]
  (check-permissions permissions
                     "categories.remove"
                     #(categories-db/upsert-category db category id)) )
