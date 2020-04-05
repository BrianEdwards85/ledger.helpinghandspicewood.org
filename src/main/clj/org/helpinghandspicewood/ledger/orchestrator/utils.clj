(ns org.helpinghandspicewood.ledger.orchestrator.utils
  (:require
    [manifold.deferred :as d]))

(defn check-permissions [permissions permission f]
  (if (some? (permissions permission))
    (f)
    (d/error-deferred (Exception. (str "User does not have " permission)))))
