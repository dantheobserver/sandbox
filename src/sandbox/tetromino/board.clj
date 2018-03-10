(ns sandbox.tetromino.board)


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
