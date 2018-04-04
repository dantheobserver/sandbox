(ns sandbox.tetromino.specs
  (:require [clojure.spec.alpha :as spec]
            [sandbox.tetromino.piece :as piece]))

(defmacro def-specs
  {:style/indent [0 :defn]}
  [& key-spec-pairs]
  (if (-> key-spec-pairs count even? not)
    `(throw (IllegalArgumentException. "`key-spec-pairs` must be in the form of key def pairs"))
    (let [pairs (reverse (partition 2 key-spec-pairs))
          defs (map (fn [[k form]] `(spec/def ~k ~form)) pairs)]
      `(do ~@defs))))

(def-specs
  ::Piece (spec/keys :req-un [::pieces ::pivot])
  ::pieces (spec/and (spec/coll-of ::coord)
             #(= (type %) clojure.lang.PersistentHashSet))
  ::pivot ::coord
  ::coord (spec/tuple ::pos ::pos)
  ::pos (spec/or :int int? :double double?))

(spec/valid? ::Piece (sandbox.tetromino.piece/->Piece #{} [0 0]))
(spec/explain ::Piece (sandbox.tetromino.piece/->Piece #{} [0 0]))
