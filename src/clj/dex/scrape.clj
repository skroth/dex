(ns dex.scrape
  (:require [net.cgrand.enlive-html :refer [select but attr? text emit* has pred first-child last-child nth-child]]
            [skyscraper.core :as core :refer [defprocessor]]
            [skyscraper.enlive-helpers :refer [href]])) 

(def seed [{:url "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_name"
            :processor :pokemon-list}])

(def type-selector
  [:table.roundy :td.roundy :table.roundy [:td (but (attr? :style))] :table :a])

(defn tables-by-header
  "Takes a vector of mixed table and header elements and returns
  a map of the header text to the associated table"
  [elements]
  (loop [els elements
         last-header nil
         table-map {}]
    (let [el (first els)
          tag (:tag el)
          next-els (rest els)]
      (if (nil? el)
        table-map
        (condp = tag
          :h3 (recur next-els (text el) table-map)
          :h4 (recur next-els (text el) table-map)
          :table (recur next-els last-header (assoc table-map last-header el))
          (recur next-els last-header table-map))))))

(defprocessor :pokemon-list
  :process-fn (fn [doc ctx]
                (let [rows (select doc [:table.roundy :tr (nth-child 3) :a])]
                  (map (fn [row]
                         {:name (first (:content row)) 
                          :url (-> row :attrs :href)
                          :processor :pokemon-detail}) rows))))

(defprocessor :pokemon-detail)
  :process-fn (fn [doc ctx]
                {:name (:name ctx)
                 :types (map text (select doc type-selector))})

(defn run-scrape []
  (core/scrape seed :sleep 1000 :parallelism 1))
