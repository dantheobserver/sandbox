(ns sandbox.sicp)

;;;;SICP

(defn -abs [x] (if (< x 0) (- x) x))
(defn -square [x] (* x x))
(defn -avg [& xs]
  (/ (apply + xs) (count xs)))


(defn improve [guess x]
  (-avg guess (/ x guess)))

(defn good-enough? [guess x]
  (< (-abs (- (-square guess) x))
     0.001))

(defn sqrt-impl
  ([x] (sqrt-impl (/ x 2) x))
  ([guess x]
   (if (good-enough? guess x)
     (float guess)
     (recur (improve guess x) x))))
