(ns sandbox.wut
  (:require [clojure.spec.alpha :as s]))


;; Takes input, say a few letters
;; query word repo
;; provide short word list
;; allow interaction (next page, type more letters to refine)
;; v1 - words that only start with provided letters, refining with further letters.




;;Take input
(def input-str-regex #"^(\w+\*?)+$")
(s/def ::word-query-type (s/and string?
                                #(>= (count %) 3)
                                #(re-matches input-str-regex %)))
(s/def ::input-str ::word-query-type)

;; These display in the console s/explain-str will return the string
#_(s/conform ::input-str "ab")
#_(s/conform ::input-str "abc")
#_(s/conform ::input-str "a*bc")
#_(s/conform ::input-str "a**bc")
#_(s/explain ::input-str "a")
#_(s/explain-str ::input-str "abc")
#_(s/explain-data ::input-str "ac")
#_(s/explain ::input-str "abc*f")
#_(s/explain ::input-str "abc**")
#_(s/explain ::input-str "*a")
#_(let [value "ab**c"
        parsed (s/conform ::input-str value)]
    (if (= parsed s/invalid?)
      (throw (ex-info "Invalid input format" (s/explain ::input-str value)))
      value))

(defn process-input
  [input-string]
  {:pre [(s/valid? ::input-str input-string)]}
  input-string)


(::input-str "abc")
