(ns sandbox.tetromino.piece
  (:require [clojure.string :as str]))

(declare print-piece)

(def print-size 2)

(defrecord Piece [pieces pivot]
  Object
  (toString [this]
    (print-piece this print-size)))

(def tetrominos {:I (Piece. #{[1 0] [1 1] [1 2] [1 3]} [1.5 1.5])
                 :L (Piece. #{[1 0] [1 1] [1 2] [2 2]} [1 1])
                 :O (Piece. #{[0 0] [0 1] [1 0] [1 1]} [0.5 0.5])
                 :Z (Piece. #{[0 0] [1 0] [1 1] [2 1]} [1 1])})

#_(map str (vals tetrominos))

(defn- coord->rel
  "Finds the cell's position relative to the pivot"
  [cell pivot]
  (vec (map - cell pivot)))

(defn- rel->coord
  "Finds the cell's coordinate given the pivot"
  [rel pivot]
  (vec (map (comp int +) rel pivot)))

#_(map (comp int +) [1 1] [1.0, 1.0])

(defn- next-pos-cw
  [[rx ry]] [(- ry) rx])

(defn- next-pos-ccw
  [[rx ry]] [ry (- rx)])

(defn next-coord-cw
  "Calculate the next rotation"
  [pivot cell]
  (-> cell
      (coord->rel pivot)
      next-pos-cw
      (rel->coord pivot)))

(defn rotate-piece
  [{:keys [pivot pieces] :as piece}]
  (->> pieces
       (map #(next-coord-cw pivot %))
       (into #{})
       (assoc piece :pieces)))

(defn- get-bound [pieces]
  (apply max (map #(apply max %) pieces)))

;; TODO: make pieces with size > 1 squared, not flat.
(defn print-piece
  "Pretty prints a piece on screen with specified size."
  [{:keys [pieces]} size]
  (let [bound (inc (get-bound pieces))
        grid-range (range bound)]
    (->> (for [y grid-range
               x grid-range]
           (if (pieces [x y]) "*" "."))
         (map #(str/join (repeat size %)))
         (partition bound)
         (mapcat #(repeat size %))
         (map str/join)
         (str/join "\n"))))

#_(println (-> tetrominos
               :I
               rotate-piece
               rotate-piece
               rotate-piece
               str))

#_(let [already-placed #{[0 1] [1 2]}
        good-piece [[0 0] [0 2]]
        bad-piece [[0 0] [0 1] [1 1]]]
    (defn conflicts?
      [piece placed]
      (boolean (some placed piece)))
    {:good-conflicts? (conflicts? good-piece already-placed)
     :bad-conflicts? (conflicts? bad-piece already-placed)})


