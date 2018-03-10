(ns sandbox.tetromino.core
  (:require [sandbox.tetromino.piece :as piece]))

(def tetrominos {:I (piece/->Piece #{[1 0] [1 1] [1 2] [1 3]} [1.5 1.5])
                 :L (piece/->Piece #{[1 0] [1 1] [1 2] [2 2]} [1 1])
                 :O (piece/->Piece #{[0 0] [0 1] [1 0] [1 1]} [0.5 0.5])
                 :Z (piece/->Piece #{[0 0] [1 0] [1 1] [2 1]} [1 1])})



#_(println (-> tetrominos
               :I
               (piece/rotate-piece :ccw)
               (piece/rotate-piece :ccw)
               ;; (piece/rotate-piece :cw)
               str))

#_(let [already-placed #{[0 1] [1 2]}
        good-piece [[0 0] [0 2]]
        bad-piece [[0 0] [0 1] [1 1]]]
    (defn conflicts?
      [piece placed]
      (boolean (some placed piece)))
    {:good-conflicts? (conflicts? good-piece already-placed)
     :bad-conflicts? (conflicts? bad-piece already-placed)})

