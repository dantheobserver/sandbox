(ns sandbox.tetromino-drop
  (:require [clojure.math.numeric-tower :as math]))

(def tetrominos {:I {:pieces [[1 0] [1 1] [1 2] [1 3]]
                     :pivot [1 2.5]}
                 :L {:pieces [[1 0] [1 1] [1 2] [2 2]]
                     :pivot [0 1]}
                 :O {:pieces [[0 0] [0 1] [1 0] [1 1]]
                     :pivot [1.5 1.5]}
                 :Z {:pieces [[0 0] [1 0] [1 1] [2 1]]
                     :pivot [1 1]
                     }})


(defn- abs [x] (max x (- x)))

(defn coord->rel
  "Finds the cell's position relative to the pivot"
  [cell pivot]
  (vec (map - cell pivot)))

(defn rel->coord
  "Finds the cell's coordinate given the pivot"
  [rel pivot]
  (vec (map + rel pivot)))

;; Cover immediate neighbor cardinals first
(defn next-diag-pos-cw
  [[rx ry :as rel-pos]]
  (if (reduce = rel-pos)
    [(- rx) ry]
    [rx (- ry)]))

(defn next-card-pos-cw
  [[rx ry]]
  (let [swapped (vector ry rx)]
    (if (= (abs ry) 1)
      (map - swapped)
      swapped)))

(defn next-coord-cw
  "get next pivot"
  [cell pivot]
  (let [rel-pos (coord->rel cell pivot)
        diag? (reduce = (map abs rel-pos))
        next-pos (if diag?
                   (next-diag-pos-cw rel-pos)
                   (next-card-pos-cw rel-pos))]
    ;; (println next-pos)
    (rel->coord next-pos pivot)))

#_(next-coord-cw [0 0] [1 1]) ;; NW
#_(next-coord-cw [2 0] [1 1]) ;; NE
#_(next-coord-cw [2 2] [1 1]) ;; SE
#_(next-coord-cw [0 2] [1 1]) ;; SW
#_(next-coord-cw [1 0] [1 1]) ;; N
#_(next-coord-cw [2 1] [1 1]) ;; E
#_(next-coord-cw [1 2] [1 1]) ;; S
#_(next-coord-cw [0 1] [1 1]) ;; W

#_(is (= 1 1) true)
(defn get-piece
  ([piece] (get-piece piece identity))
  ([piece translate] (translate (piece tetrominos))))

(defn boundary [[pieces]]
  (reduce ))
(defn rotate
  [[pieces pivot]]
  )

(defn rotate-piece-cw [piece])

(get-piece :I)
