(ns sandbox.common
  (:require [clojure.spec.alpha :as s]))

(defn- get-spec-fn [spec-def data]
  (comment
    println {:spec-def spec-def
             :data dat
             :valid (println "valid? " (s/valid? spec-def data))})

  (if (s/valid? spec-def data)
    s/conform
    s/explain-str))


(defmacro conform-ex [spec-def data]
  (let [spec-fn (get-spec-fn `~spec-def data)]
    `(~spec-fn ~spec-def ~data)))


