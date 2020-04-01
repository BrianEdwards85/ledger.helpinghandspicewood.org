(ns org.helpinghandspicewood.ledger.authentication
    (:require
        [org.helpinghandspicewood.ledger.db.users :as users]
        [aleph.http :as http]
        [manifold.deferred :as d]
        [cheshire.core :as json]
        [byte-streams :as bs]
        [buddy.sign.jwt :as jwt]))

(defn no-authorization [ctx]
    (assoc (:response ctx)
            :status 401
            :body "No Athorization"))

(defn unsign [access_token key]
    (try
        (jwt/unsign access_token key {:alg :rs256})
        (catch Exception e nil)))

(defn unsign-all [{:keys [keys]} access_token]
    (first (filter some? (map (partial unsign access_token) keys))))

(defn authorized [{:keys [keys db]} handler no-auth]
    (fn [ctx]
        (if-let [access_token (get-in ctx [:cookies "access_token"])]
            (if-let [cred (unsign-all keys access_token)]
                    (d/chain
                        (d/zip
                            (users/get-user-by-email db (:email cred))
                            (users/get-user-permissions db (:email cred)))
                        (fn [[user permissions]]
                            (if (some? user)
                                (handler (assoc ctx :token cred :user (assoc user :permissions permissions)))
                                (no-auth ctx))))
            (no-auth ctx))
        (no-auth ctx))))

(def redirect_url
    (str
        "https://edwardstx.auth0.com/authorize"
        "?state=1773"
        "&client_id=R4CvuaFs6jmUnrPOQi54iqxjEMkVBzgj"
        "&prompt=login%20"
        "&response_type=code"
        "&scope=openid%20profile%20email"
        "&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcb"
        ))

(defn redirect [ctx]
    (assoc
        (:response ctx)
        :status 302
        :headers {
            "location" redirect_url
        }))

(defn token-exchange-body [code]
    (str
        "grant_type=authorization_code"
        "&client_id=R4CvuaFs6jmUnrPOQi54iqxjEMkVBzgj"
        "&client_secret=8FdGPzsNe25zfN6iNuGEktJoEkq04g_iK6rlQT0dqiPX3UmUN4FZFHMQ5WdEcM0L"
        "&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcb"
        "&code=" code))

(defn exchange-token [code]
    (d/chain
        (http/post
            "https://edwardstx.auth0.com/oauth/token"
            {
                :headers { "Content-Type" "application/x-www-form-urlencoded" }
                :body (token-exchange-body code)
            })
        :body
        bs/to-reader
        #(json/parse-stream % true)
        )

    )

(defn callback [ctx]
    (let [{:keys [code state]} (get-in ctx [:parameters :query])]
    (d/chain
        (exchange-token code)
;;        #(do (println %1) %1)
        #(assoc
            (:response ctx)
            :status 302
            :headers {
                "Set-Cookie" (str "access_token=" (:id_token %) ";Path=/;Max-Age=" (:expires_in %) ";")
                "location" "/"
        }))))


