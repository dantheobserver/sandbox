(ns sandbox.graphics.experiments
  (:require [clojure.math.numeric-tower :as math]
            [clojure2d.core :as c]
            [clojure2d.color :as color]))
;; https://twitter.com/TedAjax/status/1004865330831712256
;; l={10,9,8,2,1}
;; ::_::
;; cls()srand()for m=0,99 do ;;<<-srand seed random
;; x=rnd(8)-4
;; z=rnd(8)-4
;; y=rnd(14)-7
;; for n=4,0,-1 do
;; a=t()/4+n*.01
;; b=t()/4+(n+1)*.01
;; c=cos(a)*x-sin(a)*z
;; d=sin(a)*x+cos(a)*z+7
;; g=64+(c*64)/d
;; h=64+(y*64)/d
;; circfill(g,h,max((7-d)+n/2,0),l[4-n+1])
;; end
;; end
;; flip()goto _


;; Nebulae
;; e=2.71828
;; ::_::
;; cls()
;; srand()
;; for n=2,0,-1 do
;; for i=min(n,5),100 do
;; x=cos(e*i-t()/(e*2)-n/(e*7))*(i-n)/e*3+64
;; y=sin(e*i-t()/(e*1)-n/(e*10))*(i-n)/e*3+64
;; circ(x,y,2-(i/28)+((sin(t()/2+rnd(50)/50)+1)/2*1),12-(n%2))
;; end
;; end
;; flip()goto _

;; Creates the initial draw state, is passed into draw fn
(def mu-unit 1)
(def grav-mod 0.5)
(defn mu
  "Mass Unit"
  ([] (mu 1))
  ([factor] (* factor mu-unit)))

(defn setup [canvas window]
  {:orbs #{{:name "Tiny Astral Body"
           :size [40 40]
           :mass (mu 1) ;;some 
           :pos [55 55]
           :color :white}
          {:name "Huge Planet" ;; Massive stationary object
           :size [220 220]
           :mass (mu 10)
           :pos [300 300]
           :color :brown}}})

(def square* #(math/expt % 2))

(defn vector-mag* [x y]
  (let [mag (math/sqrt (+ (square* x) (square* y)))]
    mag))

(defn +v* [va vb] (into [] (map + va vb)))

;; TODO: take in listof mass object and accumulate calculated vectors
;; TODO: should be some modifier of the mass, perhaps a fraction.
(defn point-grav-vector
  "given a list of objects and their "
  [massive-objects x y]
  (loop [[{[xm ym] :pos, mass :mass, :as obj} & rest] massive-objects
         net-force-vec [0.0 0.0]]
    (println obj)
    (if obj
      (let [[dx dy :as d-vec] [(- xm x) (- ym y)]
            distance (vector-mag* dx dy)
            unit-vec (map #(/ % 2) d-vec)
            force-coeff (/ (* mass grav-mod) distance) ;;TODO: grav mod should be affected object's mass (m-b/(* d m-affected)) 
            force-vec (map #(* force-coeff %) d-vec)]
        (recur rest (+v* force-vec net-force-vec)))
      net-force-vec)))

(defn moved-orbs* [orbs]
  (if (= 1 (count orbs))
    orbs
    (->
     (for [{[x y] :pos :as orb} orbs
           :let [others (disj orbs orb)
                 grav-v (point-grav-vector orbs x y)]]
       (update orb :pos #(+v* % grav-v))))))

(defn orb-render
  [canvas {:keys [size pos color]}]
  (let [[w h] size
        [x y] pos]
    (-> canvas
        (c/set-color color)
        (c/ellipse x y w h))))

(defn next-state [state]
  (-> state
      (update :orbs moved-orbs*)))

;; Notes:
;; * Canvas already in graphical context, no need to use with-canvas.
;; * state is local drawing function state, not global
;; * Global state global for each windowreturned by setup;
;;   (get-state window) (set-state! window) get/set respectively.
(defn draw [canvas window frame {:keys [orbs] :as state}]
  (doall ;; TODO: needed?
   (for [orb orbs]
     (-> canvas
         (orb-render orb))))
  (next-state state))

(def field-size [500 500])
(c/with-canvas [canvas (apply c/canvas field-size)]
  (c/show-window
   {:canvas canvas
    :fps 30
    :draw-fn draw
    :setup setup ;; Returns the initial draw stae.
    ;; :refresher :fast ;;<- default `:safe`
    }))

