(ns trump-card.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [taoensso.timbre :refer [warn]]))

(def config
  (if-let [p (io/resource "config.edn")]
    (aero/read-config p {})
    (do
      (warn "Missing config file `config.edn`")
      {})))

(defn merge-with-config [x]
  (merge-with merge config x))
