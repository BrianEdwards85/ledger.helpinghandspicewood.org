(defproject org.helpinghandspicewood/ledger "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
    [org.clojure/clojure "1.10.0"]
    [org.clojure/spec.alpha "0.2.176"]
    [org.clojure/tools.nrepl "0.2.13"]
    [org.clojure/data.json "1.0.0"]
    [org.clojure/core.async "1.0.567"]

    [org.postgresql/postgresql "42.2.10.jre7"]
    [org.flywaydb/flyway-core "6.2.4"]
    [com.layerware/hugsql "0.5.1"]
    [hikari-cp "2.10.0"]

    [aleph "0.4.6"]
    [manifold "0.1.8"]
    [com.stuartsierra/component "0.4.0"]
    [yogthos/config "1.1.7"]

    [yada "1.2.15.1"]
    [yada/aleph "1.2.15.1"]
    [hiccup "1.0.5"]
    [com.walmartlabs/lacinia "0.36.0"]

    [buddy/buddy-core "1.6.0"]
    [buddy/buddy-sign "3.1.0"]

    [cheshire "5.10.0"]

    [org.apache.logging.log4j/log4j-core "2.13.0"]
    [org.apache.logging.log4j/log4j-api "2.13.0"]
    [org.apache.logging.log4j/log4j-slf4j-impl "2.13.0"]
    ]
  :main ^:skip-aot org.helpinghandspicewood.ledger
  :target-path "target/%s"
  :source-paths [ "src/main/clj"]
  :resource-paths ["src/main/resource"]
  :profiles {:uberjar {:aot :all}})
