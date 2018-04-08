(ns sandbox.graphics.star-field
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [com.rpl.specter :as s]))

(def field-size [800 600])

(defn move-star
  [star]
  (let [[vx vy] (:vec star)]
    (-> star
        (update-in [:pos 0] (partial + vx))
        (update-in [:pos 1] (partial + vx)))))
(defn will-hit-bounds?
  [{rad :rad, [x y] :pos, [dx dy] :vec} bounds]
  (let [half-r (/ rad 2)
        [bx by] bounds
        [nx ny] [(+ x dx) (+ y dy)]]
    (or (<= (- nx half-r) 0)
        (<= (- ny half-r) 0)
        (<= bx (+ nx half-r))
        (<= by (+ ny half-r)))))

;; draw functions
(defn setup []
  (q/frame-rate 30)
  (let [[sx sy] (map #(/ % 2) field-size)]
    {:star {:rad 20
            :pos [sx sy]
            :vec [16 16]}}))

(defn update-scene [state]
  (let [collides? #(will-hit-bounds? % field-size)]
    ;;just star
    (s/multi-transform
     [:star
      (s/multi-path
       [(complement collides?) (s/terminal move-star)]
       [collides? :vec s/ALL (s/terminal -)])]
     state)))

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
