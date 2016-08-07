(ns trump-card.system-spec
  (:require [peripheral
             [component :refer [defcomponent]]
             [utils :refer [is-class-name?]]]
            [peripheral.system
             [analysis :as analysis]
             [subsystem :as subsystem]]
            [com.stuartsierra.dependency :as dep]
            [com.stuartsierra.component :as component]))

(defprotocol SystemUsable
  (to-system-using [value]))

(extend-protocol SystemUsable
  clojure.lang.Sequential
  (to-system-using [sq]
    (into {} (map to-system-using) sq))

  clojure.lang.IPersistentMap
  (to-system-using [m]
    {:pre [(every? keyword? (keys m))
           (every? keyword? (vals m))]}
    m)

  clojure.lang.Keyword
  (to-system-using [k]
    {k k})

  clojure.lang.Symbol
  (to-system-using [s]
    (let [k (-> s name keyword)]
      {k k}))

  nil
  (to-system-using [_]
    {}))

(defn analyze
  [args]
  (->> args
   (partition 2)
   (map (fn [[k using]]
          [k (to-system-using using)]))
   (into {})))

(defn system-factory
  [deps comps]
  (let [deps (analyze deps)
        sys' (component/system-using comps deps)]
    sys'))

(comment
  (system-factory
   [:bar []
    :foo '[bar]
    :baz '[bar]]
   {:bar {} :foo {} :baz {}})
  )
