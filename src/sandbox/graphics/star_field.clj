(ns sandbox.graphics.star-field
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def field-size [800 600])

(defn move-star
  [star]
  (let [[dx dy] (:vec star)]
    (-> star
        (update :x (partial + dx))
        (update :y (partial + dy)))))

(defn hit-bounds?
  [{:keys [x y rad]} bounds]
  (let [half-r (/ rad 2)
        [bx by] bounds]
    (or (<= (- x half-r) 0)
        (<= (- y half-r) 0)
        (<= bx (+ x half-r))
        (<= by (+ y half-r)))))

(defn setup []
  (q/frame-rate 30)
  (let [[sx sy] (map #(/ % 2) field-size)]
    {:star {:rad 20
            :x sx :y sy
            :vec [2 2]}}))

#_(let [nested {:a {:b 1 :c {:d 1}}}
        {{b :b} :a} nested]
    b)

(defn update-scene [state]
  (let [star (:star state)
        moved-star (move-star star)]
    (if (hit-bounds? moved-star field-size)
      (let [star-reversed (update moved-star :vec (partial map -))]
        (assoc state :star star-reversed))
      (assoc state :star moved-star))))

#_(update-scene {:star {:rad 20
                        :x 1 :y 1
                        :vec [1 1]}})

(defn draw
  [{:keys [star]}]
  (q/background 3 140 170)
  (println star)
  (let [{:keys [x y rad]} star]
    (q/ellipse x y rad rad)))

(q/defsketch starfield
  :title "Filled with stars"
  :size field-size
  :setup setup
  :update update-scene
  :draw draw
  :middleware [m/fun-mode])
