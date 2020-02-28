(ns dex.scrape
  (:require [net.cgrand.enlive-html :refer [select attr? text emit* has pred first-child last-child nth-child]]
            [skyscraper.core :as core :refer [defprocessor]]
            [skyscraper.enlive-helpers :refer [href]])) 

(def seed [{:url "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_name"
            :processor :pokemon-list}])

(defprocessor :pokemon-list
  :process-fn (fn [doc ctx]
                (let [rows (select doc [:table.roundy :tr (nth-child 3) :a])]
                  (map (fn [row]
                         {:name (first (:content row)) 
                          :url (-> row :attrs :href)
                          :processor :pokemon-detail}) rows))))

(defprocessor :pokemon-detail)
; TODO: write this processor!
;   :process-fn (fn [doc ctx]
                ; {:name (:name ctx)}))

(defn run-scrape []
  (core/scrape seed :sleep 1000 :parallelism 1))