(ns trump-card.quick-user
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]))

(defn- set-init! [fn]
  (reloaded.repl/set-init! fn))

(defmacro setup! [system-ctor]
  `(do
     (set-init! ~system-ctor)
     (def ~'system system)
     (def ~'init init)
     (def ~'start start)
     (def ~'go go)
     (def ~'reset reset)
     (def ~'reset-all reset-all)))
