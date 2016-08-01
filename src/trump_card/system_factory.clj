(ns trump-card.system-factory
  (:require [peripheral.core :as p]
            [trump-card.config :refer [merge-with-config]]))

(defmacro defsystemfactory [id & xs]
  (assert (not (nil? (next xs))))
  (assert (odd? (count xs)))
  (let [dep-map (butlast xs)
        ctor (last xs)
        ctor-name (symbol (str "new-" id))
        map-converter (symbol (str "map->" id))
        start-name (symbol (str "start-" id))]
    `(do
       (p/defsystem+ ~id []
         ~@dep-map)
       (defn ~ctor-name []
         (-> (do ~ctor)
             ~map-converter
             merge-with-config))
       (def ~start-name (comp p/start ~ctor-name)))))
