(ns sandbox.macros)

#_(>_< 4
     (/ 8 _)
     (+ _ 3))
;;=> 5

(defmacro >_< [input & rest]
  (map (fn [form]
         )))


((fn [a [first & rest]]
   rest)
 1 )
#_(map #(if (= `_ %) 1 %)
     `(+ 1 _ 2))

