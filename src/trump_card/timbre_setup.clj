(ns trump-card.timbre-setup
  (:require [trump-card.config :refer [config]]
            [taoensso.timbre :as timbre]))

(when (:timbre config)
  (timbre/merge-config! (:timbre config))
  (timbre/debug "Loaded timbre config section"))
