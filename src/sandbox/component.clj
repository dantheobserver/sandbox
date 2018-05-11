(ns sandbox.component
  (:require [com.stuartsierra.component :as component]))

(defrecord GitApi [url key]
  java.lang.Object
  (toString [this]
    (str "Connected to " (:url this)))
  component/Lifecycle
  (start [component]
    (println "starting")))

(defrecord View [])

(component/using)

(component/start)

(defmacro top-level
  "This is the macro-level doc"
  [documented-form]
  )

(top-level []
           Dan
           (second-level))
