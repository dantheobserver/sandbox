(ns sandbox.trig
  (:import [java.lang.Math]))

(let [rad (Math/toRadians 30)]
  (->> (Math/sin rad)
       rationalize
       (fn [])))

(defn- sin [x] (Math/sin x))
(defn- deg->rad [d] (Math/toRadians d))

(defn triangle-area [x]
  (-> (/ (Math/sin x)
         (* 2 (Math/sin (/ x 2))))))

(triangle-area (deg->rad 90))

(def triangle-proportions {:a 1
                           :b 1
                           :c })

(defn get-triangle-area
  (:c ))

(defn quarter-area [start]
  (if (= start 0)
    0
    (let [rng (reverse (range 0 start))]
      )))
