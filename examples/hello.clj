(ns trump-card.examples.hello
  (:require [trump-card.core :as tc]))



(tc/defsystemfactory sys
  :foo [:bar]
  {:bar 10})

(tc/setup-user! new-sys)
