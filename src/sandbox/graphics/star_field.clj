(ns sandbox.graphics.star-field
  (:require [com.rpl.specter :as s]
            [quil.core :as q]
            [quil.middleware :as m]
            [sandbox.graphics.stars :as stars]))

(def field-size [300 200])

;; Draw functions
(defn setup []
  (q/frame-rate 30)
  (let [[sx sy] (map #(/ % 2) field-size)]
    {:star {:rad 20
            :pos [sx sy]
            :vec [1 1]}}))

(defn update-scene [state]
  (update state :star stars/move-star field-size))

(defn draw
  [{:keys [star]}]
  (q/background 3 140 170)
  (let [{[x y] :pos, rad :rad} star]
    (q/ellipse x y rad rad)))

(q/defsketch starfield
  :title "Filled with stars"
  :size field-size
  :setup setup
  :update update-scene
  :draw draw
  :middleware [m/fun-mode])
