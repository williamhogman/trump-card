(ns trump-card.core
  (:require
   [potemkin :refer [import-vars]]
   [trump-card.timbre-setup]))

(import-vars
 [trump-card.config
  config]
 [trump-card.quick-user
  setup-user!])
