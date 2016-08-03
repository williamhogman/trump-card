(ns trump-card.components.riemann
  (:require [peripheral.core :as p]
            [riemann.client :as c]
            [taoensso.tufte :as tufte]))

(defprotocol EventReporter
  "Protocol for components allowing event reporting"
  (send-event [event-reporter event]))


(defn- tufte->riemann [x]
  x)

(defn- tufte-handler [riemann-client data]
  (->> data
       tufte->riemann
       (send-event riemann-client)))

(p/defcomponent RiemannClient [riemann-host]
  :p/started
  (fn [this]
    (let [id (gen-sym "tufte-handler-id")]
      (tufte/add-handler! tufte-handler-id (partial tufte-handler this))
      (assoc this :tufte-handler-id id)))
  :p/stop
  (fn [this]
    (tufte/remove-handler! (:tufte-handler-id this))
    (dissoc this :tufte-handler-id))
  :client (c/tcp-client {:host riemann-host})
  #(c/close! client)
  EventReporter
  (send-event [this event]
              (c/send-event client event)))

(defn new-riemann-client []
  (map->RiemannClient {}))
