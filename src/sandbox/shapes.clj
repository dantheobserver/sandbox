(ns sandbox.shapes
  (:require [clojure.spec.alpha :as s]))

(s/def ::rgb (s/tuple int? int? int?))
(s/def ::named string?)
(s/def ::color (s/or :rgb ::rgb
                     :named ::named))
(s/def ::dimensions (s/and (s/coll-of number?)
                           #(> (count %) 1)))
(s/def ::Shape (s/keys :req [::color ::dimensions]))


(s/explain ::color "test")
(let [shape {::color "white"
             ::dimensions [1 1.0]}]
  (map #(% ::Shape shape)
       [s/conform s/explain-data])) 

(defn circle)


