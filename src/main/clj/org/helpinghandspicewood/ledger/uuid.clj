(ns org.helpinghandspicewood.ledger.uuid)

(defn uuid [] (str (java.util.UUID/randomUUID)))