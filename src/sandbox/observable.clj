(ns sandbox.observable
  (:require [clojure.spec.alpha :as s]))

(s/def ::obs-value some?)
(s/def ::obs-fn (s/fspec :args ::obs-value
                         :ret ::obs-value
                         :fn #(= (:args %)
                                 (:ret %))))
(s/def ::observable (s/fdef :args ))

(defn obs-value-fn
  ([] (obs-value-fn nil))
  ([val]
   (fn [] val)))

(s/fdef obs-value-fn
  :args (s/alt :nothing nil? :initial ::obs-value)
  :ret ::obs-fn)

(let [fn (obs-value-fn)])


