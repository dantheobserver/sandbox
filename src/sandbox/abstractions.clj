(ns sandbox.abstractions
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.properties :as prop]))

;; Distinguish between shapes
(s/def ::CutOut some?)
(s/def ::Shape some?)

(defn shape-of [cut-out])

(s/fdef shape-of
        :args (s/cat :cut-out ::CutOut
                     :ret ::Shape))

;; Construct shapes
(s/def ::Color some?)

(defn rect [color width height])

(s/fdef rect
        :args (s/cat :color ::Color
                     :width number?
                     :height number?)
        :ret ::CutOut)

(defn ellipse [color width height])

(s/fdef ellipse
        :args (s/cat :color ::Color
                     :width number?
                     :height number?)
        :ret ::CutOut)


;; Preservation of shape
(defn translate [cut-out tx ty])

(defn rotate [cut-out r])
(s/fdef rotate
        :args (s/cat :cut-out ::CutOut :r number?)
        :ret ::CutOut
        :fn #(= (shape-of (-> % :ret))
                (shape-of (-> % :args :cut-out))))

;; Preservation of color
(defn color-of [cut-out])

(s/fdef color-of
        :args (s/cat :cut-out ::CutOut)
        :ret ::CutOut)

(s/fdef translate
        :args (s/cat :cut-out ::CutOut :tx number? :ty number?)
        :ret ::CutOut
        :fn (s/and #(= (shape-of (-> % :ret))
                       (shape-of (-> % :args :cut-out)))
                   #(= (color-of (-> % :ret))
                       (color-of (-> % :args :cut-out)))))

;; Overlay order [corner-case avoid]
(comment
  (defn overlay [cut-out-a cut-out-b])

  (s/fdef overlay
          :args (s/cat :cut-out-a ::CutOut
                       :cut-out-b ::CutOut)
          :ret ::CutOut))

;; Overlay order (composable)

(s/def ::Collage some?)

(defn overlay [collage cut-out])

(s/fdef overlay
  :args (s/cat :collage ::Collage
          :cut-out ::CutOut)
  :ret ::Collage)


