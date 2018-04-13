(ns sandbox.graphics.stars
  (:require [com.rpl.specter :as s]))

(defn next-pos
  [star]
  (let [[vx vy] (:vec star)]
    (-> star
        (update-in [:pos 0] (partial + vx))
        (update-in [:pos 1] (partial + vx)))))

(def ^:private hit-transforms {:LR [- +]
                               :TB [+ -]})

(defn boundary-hit
  [{rad :rad, [x y] :pos, [dx dy] :vec} bounds]
  (let [half-r (/ rad 2)
        [bx by] bounds
        [nx ny] [(+ x dx) (+ y dy)]]
    (cond (or  (<= (- nx half-r) 0)
               (<= bx (+ nx half-r))) :LR
          (or (<= (- ny half-r) 0)
              (<= by (+ ny half-r))) :TB)))


;; TODO: Need to account for boundaries, can't just go by directions
(defn move-star
  [star bounds]
  (if-let [hit-bound (boundary-hit star bounds)]
    (let [[sx sy] (hit-transforms hit-bound)]
      (s/multi-transform
       [:vec (s/multi-path [0 (s/terminal sx)]
                           [1 (s/terminal sy)])]
       star))
    (next-pos star)))
