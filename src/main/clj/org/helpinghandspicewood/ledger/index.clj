(ns org.helpinghandspicewood.ledger.index
    (:require [hiccup.page :refer [include-js include-css html5]]))

(defn index [_]
    (html5
        [:head
            [:meta {:charset "utf-8"}]
            [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
            [:title "Ledger"]]

        [:body
            [:div#root ]
            (include-js "/assets/js/main.js")
        ]))


(comment

    https://edwardstx.auth0.com/login
    ?state=g6Fo2SB4WnluWm9FT25SQVViekxwbGxDbUUzRkhyMk9fazRRdKN0aWTZIFdkalpfbUNranR6U2h6REw0bWRqS0tYNHhFelVPR0Mzo2NpZNkgVHNudkkyWHJkR3ZRbEdQTlpOTVQ1ZjIyR3E4Q0lkQ08
    &client=TsnvI2XrdGvQlGPNZNMT5f22Gq8CIdCO
    &protocol=oauth2&prompt=login &response_type=code&connection=Username-Password-Authentication&scope=openid%20profile
    &redirect_uri=https%3A%2F%2Fmanage.auth0.com%2Ftester%2Fcallback%3Fconnection%3DUsername-Password-Authentication
    )