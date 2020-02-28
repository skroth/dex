(defproject dex "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[ch.qos.logback/logback-classic "1.2.3"]
                 [cheshire "5.9.0"]
                 [cljs-ajax "0.8.0"]
                 [clojure.java-time "0.3.2"]
                 [com.cognitect/transit-clj "0.8.319"]
                 [com.google.javascript/closure-compiler-unshaded "v20191027" :scope "provided"]
                 [conman "0.8.4"]
                 [cprop "0.1.15"]
                 [day8.re-frame/http-fx "0.1.6"]
                 [expound "0.8.3"]
                 [funcool/struct "1.4.0"]
                 [luminus-jetty "0.1.7"]
                 [luminus-migrations "0.6.6"]
                 [luminus-transit "0.1.2"]
                 [luminus/ring-ttl-session "0.3.3"]
                 [markdown-clj "1.10.1"]
                 [metosin/muuntaja "0.6.6"]
                 [metosin/reitit "0.3.10"]
                 [metosin/ring-http-response "0.9.1"]
                 [mount "0.1.16"]
                 [nrepl "0.6.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597" :scope "provided"]
                 [org.clojure/core.async "0.4.500"]
                 [org.clojure/google-closure-library "0.0-20191016-6ae1f72f" :scope "provided"]
                 [org.clojure/google-closure-library-third-party "0.0-20191016-6ae1f72f" :scope "provided"]
                 [org.clojure/tools.cli "0.4.2"]
                 [org.clojure/tools.logging "0.5.0"]
                 [org.postgresql/postgresql "42.2.9"]
                 [org.webjars.npm/bulma "0.8.0"]
                 [org.webjars.npm/material-icons "0.3.1"]
                 [org.webjars/webjars-locator "0.38"]
                 [re-frame "0.10.9"]
                 [reagent "0.9.0-rc3"]
                 [ring-webjars "0.2.0"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [selmer "1.12.18"]
                 [thheller/shadow-cljs "2.8.69" :scope "provided"]
                 [skyscraper "0.3.0"]]

  :min-lein-version "2.0.0"
  
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/clj"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :main ^:skip-aot dex.core

  :plugins [[lein-shadow "0.1.7"]]
  :clean-targets ^{:protect false}
  [:target-path "target/cljsbuild"]
  :shadow-cljs
  {:nrepl {:port 7002}
   :builds
   {:app
    {:target :browser
     :output-dir "target/cljsbuild/public/js"
     :asset-path "/js"
     :modules {:app {:entries [dex.app]}}
     :devtools
     {:watch-dir "resources/public" :preloads [re-frisk.preload]}
     :dev
     {:closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}}}
    :test
    {:target :node-test
     :output-to "target/test/test.js"
     :autorun true}}}
  
  :npm-deps [[shadow-cljs "2.8.69"]
             [create-react-class "15.6.3"]
             [react "16.8.6"]
             [react-dom "16.8.6"]]

  :profiles
  {:uberjar {:omit-source true
             :prep-tasks ["compile" ["shadow" "release" "app"]]
             
             :aot :all
             :uberjar-name "dex.jar"
             :source-paths ["env/prod/clj" "env/prod/cljs"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev  {:jvm-opts ["-Dconf=dev-config.edn"]
                  :dependencies [[binaryage/devtools "0.9.11"]
                                 [cider/piggieback "0.4.2"]
                                 [pjstadig/humane-test-output "0.10.0"]
                                 [prone "2019-07-08"]
                                 [re-frisk "0.5.4.1"]
                                 [ring/ring-devel "1.8.0"]
                                 [ring/ring-mock "0.4.0"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.24.1"]
                                 [jonase/eastwood "0.3.5"]]
                  
                  
                  :source-paths ["env/dev/clj" "env/dev/cljs" "test/cljs"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user
                                 :timeout 120000}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:jvm-opts ["-Dconf=test-config.edn"]
                  :resource-paths ["env/test/resources"]
                  
                  
                  }
   :profiles/dev {}
   :profiles/test {}})
