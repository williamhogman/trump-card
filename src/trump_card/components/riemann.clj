(ns trump-card.components.riemann
  (:require [peripheral.core :as p]
            [riemann.client :as c]))

(defprotocol EventReporter
  "Protocol for components allowing event reporting"
  (send-event [event-reporter event]))


(p/defcomponent RiemannClient [riemann-host]
  :client (c/tcp-client {:host riemann-host})
  #(c/close! client)
  EventReporter
  (send-event [this event]
              (c/send-event client event)))

(defn new-riemann-client []
  (map->RiemannClient {}))
