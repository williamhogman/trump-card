(ns trump-card.quick-user
  (:require [reloaded.repl :refer [set-init!]]
            [potemkin :refer [import-vars]]))

(defmacro setup-user! [system-ctor]
  `(do
     (set-init! ~system-ctor)
     (import-vars [reloaded.repl system init start stop go reset reset-all])))
