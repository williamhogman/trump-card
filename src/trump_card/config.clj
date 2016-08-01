(ns trump-card.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]))

(def config
  (if-let [p (io/resource "config.edn")]
    (aero/read-config p {})))

(defn merge-with-config [x]
  (merge-with merge config x))
