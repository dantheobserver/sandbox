(ns sandbox.graphics.stars
  (:require [com.rpl.specter :refer :all]))

(defn next-pos
  [{:keys [vec] :as star}]
  (transform [:pos INDEXED-VALS (collect-one FIRST) LAST]
             (fn [i p]
               (+ (vec i) p))
             star))

(def ^:private
  hit-transforms {:LR [- +], :TB [+ -]})

(defn- boundary [pos r]
  (let [half-r (/ r 2)]
    [(- pos half-r) (+ pos half-r)]))

(defn- exceeds-bounds? [[pos-floor pos-ciel] bound]
  (not (< 0 pos-floor pos-ciel bound)))

(defn boundary-hit?
  [{rad :rad, [x y] :pos, [dx dy] :vec} bounds]
  (let [[bx by] bounds
        [nx ny] [(+ x dx) (+ y dy)]
        star-x-bounds (boundary nx rad)
        star-y-bounds (boundary ny rad)]
    (cond (exceeds-bounds? star-x-bounds bx) :LR
          (exceeds-bounds? star-y-bounds by) :TB)))

(defn move-star
  [star bounds]
  (if-let [hit-bound (boundary-hit? star bounds)]
    (let [[sx sy] (hit-transforms hit-bound)]
      (multi-transform
       (multi-path [:vec 0 (terminal sx)]
                   [:vec 1 (terminal sy)]
                                        ;[:pos 0 ]
                   ) ; set it to the max/min-edge position if hit bound on next move
       star))
    (next-pos star)))
