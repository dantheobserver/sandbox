(ns sandbox.tetromino.piece
  (:require [clojure.string :as str]))

(declare print-piece)

(defrecord Piece [pieces pivot]
  Object
  (toString [this]
    (print-piece this 1)))

(defn- coord->rel
  "Finds the cell's position relative to the pivot"
  [cell pivot]
  (vec (map - cell pivot)))

(defn- rel->coord
  "Finds the cell's coordinate given the pivot"
  [rel pivot]
  (vec (map (comp int +) rel pivot)))

(def rotate-fns {:cw (fn [[rx ry]] [(- ry) rx])
                 :ccw (fn [[rx ry]] [ry (- rx)])})

(defn next-coord-cw
  "Calculate the next rotation"
  [pivot cell next-pos-fn]
  (-> cell
      (coord->rel pivot)
      next-pos-fn
      (rel->coord pivot)))

;; TODO: create specs
(defn rotate-piece
  [{:keys [pivot pieces] :as piece} direction] 
  (->> pieces
       (map #(next-coord pivot % (rotate-fns direction)))
       (into #{})
       (assoc piece :pieces)))

(defn- get-bound [pieces]
  (apply max (map #(apply max %) pieces)))

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


