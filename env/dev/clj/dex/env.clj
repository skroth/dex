(ns dex.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [dex.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[dex started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[dex has shut down successfully]=-"))
   :middleware wrap-dev})
