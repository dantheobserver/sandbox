(ns sandbox.tetromino.piece
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as spec]))

(declare print-piece)

(defrecord Piece [pieces pivot]
  Object
  (toString [this]
    (print-piece this 1)))

(defn size
  [{:keys [pieces]}]
  (reduce (fn [[x y] [nx ny]]
            [(max x nx) (max y ny)])
          pieces))

(defn- coord->rel
  "Finds the cell's position relative to the pivot"
  [cell pivot]
  (vec (map - cell pivot)))

(defn- rel->coord
  "Finds the cell's coordinate given the pivot"
  [rel pivot]
  (vec (map (comp int +) rel pivot)))

(def ^:private rotate-fns {:cw (fn [[rx ry]] [(- ry) rx])
                           :ccw (fn [[rx ry]] [ry (- rx)])})

(defn next-coord
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

(defn bounds [piece]
  (apply max (map #(apply max %) (piece :pieces))))

(defn print-piece
  "Pretty prints a piece on screen with specified size."
  [{:keys [pieces] :as piece} size]
  (let [bound (inc (bounds piece))
        grid-range (range bound)]
    (->> (for [y grid-range
               x grid-range]
           (if (pieces [x y]) "*" "."))
         (map #(str/join (repeat size %)))
         (partition bound)
         (mapcat #(repeat size %))
         (map str/join)
         (str/join "\n"))))


