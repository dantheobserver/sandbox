(ns sandbox.mating-objects.core)

;; *. Generate random objects with randomly generated set of properties
;; *. Apply randomly generated functions with random arguments that modify objects.
;; *. First function applied to an empty object creates the initial object, subsequent modify;
;; *. Keep history of these changes and the objects therein applied n times
;; *. Randomly choose a function that will act as a seed for choosing other functions in the history
;; *. See the property of those functions in relation to

(defn apply-morph
  [object morph-obj]
  (if (= object {})
    morph-obj
    let [morph-fn (get-morph-fn morph-obj)]
    (morph-fn object))))

(eval '(a-plus a 2))

