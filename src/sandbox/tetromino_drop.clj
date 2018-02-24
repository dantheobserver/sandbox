(ns sandbox.tetromino-drop
  (:require [clojure.string :as str]))

(def tetrominos {:I {:pieces #{[1 0] [1 1] [1 2] [1 3]}
                     :pivot [1.5 1.5]}
                 :L {:pieces #{[1 0] [1 1] [1 2] [2 2]}
                     :pivot [1 1]}
                 :O {:pieces #{[0 0] [0 1] [1 0] [1 1]}
                     :pivot [0.5 0.5]}
                 :Z {:pieces #{[0 0] [1 0] [1 1] [2 1]}
                     :pivot [1 1]}})

(defn- coord->rel
  "Finds the cell's position relative to the pivot"
  [cell pivot]
  (vec (map - cell pivot)))

(defn- rel->coord
  "Finds the cell's coordinate given the pivot"
  [rel pivot]
  (vec (map + rel pivot)))

(defn- next-pos-cw [[rx ry]] [(- ry) rx]) ;; It was that simple!?

(defn next-coord-cw
  "Calculate the next rotation"
  [pivot cell]
  (-> cell
      (coord->rel pivot)
      next-pos-cw
      (rel->coord pivot)))

(defn rotate-piece
  [{:keys [pivot pieces] :as piece}]
  (let [next-coord (into #{} (map #(next-coord-cw pivot %)) pieces)]
    (->> pieces
         (map #(next-coord-cw pivot %))
         (into #{})
         (assoc piece :pieces))))

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
