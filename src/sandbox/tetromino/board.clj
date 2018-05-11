(ns sandbox.tetromino.board
  ^{:doc "Operations on a tetromino board"}
  (:require [sandbox.tetromino.piece :as piece]
            [sandbox.tetromino.specs :as specs]))

(declare print-board)

;; TODO: definitely needs specs
(defrecord Board [width height pieces current-piece]
  Object
  (toString [this]
    (print-board this)))

(defrecord BoardPiece [x y piece])

(defn place-piece
  "Add a `piece` to the `board` at `x-pos`.
  Given a `piece` and an `x-pos`, will return the board
  with the piece as the current. If  `x-pos` is beyond the bounds
  of the board, will be placed at the nearest boundary."
  [{:keys [width] :as board} piece x-pos]
  (let [[px py] (piece/bounds piece)
        offset 
        overflow (- offset x)
        piece-coord [(- x-pos overflow) (- 0 py)]] 
    (assoc piece
           :current-piece
           {:piece piece, :pos piece-coord})))

#_(ns-map 'sandbox.tetromino.board)

#_(println (-> tetrominos
               :I
               rotate-piece
               rotate-piece
               rotate-piece
               str))

#_(let [already-placed #{[0 1] [1 2]}
        good-piece [[0 0] [0 2]]
        bad-piece [[0 0] [0 1] [1 1]]]
    (defn conflicts?
      [piece placed]
      (boolean (some placed piece)))
    {:good-conflicts? (conflicts? good-piece already-placed)
     :bad-conflicts? (conflicts? bad-piece already-placed)})
