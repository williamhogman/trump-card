(ns trump-card.components.riemann
  (:require [peripheral.core :as p]
            [riemann.client :as c]
            [trump-card.components.riemann :as r]
            [taoensso.tufte :as tufte]))

(defn- tufte->riemann
  "Coverts a tufte measurement to a riemann measurement"
  [x] x)

(defn- tufte-handler
  "Handles incoming tufte measurements pushing them to riemann"
  [riemann-client data]
  (->> data
       tufte->riemann
       (r/send-event riemann-client)))

(defn- add-handler! [this]
  (let [id (gen-sym "tufte-handler-id")
        handler (partial tufte-handler (:riemann this))]
    (tufte/add-handler! tufte-handler-id handler)
    id))

(defn- remove-handler! [this]
  (tufte/remove-handler! (:tufte-handler-id this))


(p/defcomponent TufteRiemannAdapter [riemann]
  :p/started
  (fn [this]
    (let [id (add-handler! this)]
      (assoc this :tufte-handler-id id)))
  :p/stop
  (fn [this]
    (remove-handler! this)
    (dissoc this :tufte-handler-id)))
