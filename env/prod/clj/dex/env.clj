(ns dex.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dex started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[dex has shut down successfully]=-"))
   :middleware identity})
