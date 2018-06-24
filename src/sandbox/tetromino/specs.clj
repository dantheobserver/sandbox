(ns sandbox.tetromino.specs
  (:require [clojure.spec.alpha :as s]
            [sandbox.tetromino.piece :as piece]))

;; (defmacro def-specs
;;   {:style/indent 0}
;;   [& key-spec-pairs]
;;   (if (-> key-spec-pairs count even? not)
;;     `(throw (IllegalArgumentException. "`key-spec-pairs` must be in the form of key def pairs"))
;;     (let [pairs (reverse (partition 2 key-spec-pairs))
;;           defs (map (fn [[k form]] `(spec/def ~k ~form)) pairs)]
;;       `(do ~@defs))))

;; (def-specs
;;   ::Piece (spec/keys :req-un [::pieces ::pivot])
;;   ::pieces (spec/and (spec/coll-of ::coord)
;;                      #(= (type %) clojure.lang.PersistentHashSet))
;;   ::pivot ::coord
;;   ::coord (spec/tuple ::scalar ::scalar)
;;   ::scalar (spec/or :int int? :double double?))

;; (def-specs
;;   ::Board (spec/keys :req-un [::width ::height ::pieces ::current-piece])
;;   ::width ::scalar
;;   ::height ::scalar
;;   ::current-piece)

#_(spec/valid? ::Piece (sandbox.tetromino.piece/->Piece #{} [0 0]))
#_(spec/conform ::Piece (sandbox.tetromino.piece/->Piece #{} [0 0]))
