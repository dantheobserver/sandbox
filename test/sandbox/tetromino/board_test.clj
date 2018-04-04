(ns sandbox.tetromino.board-test
  (:require [sandbox.tetromino.board :as board]
            [clojure.test :refer [is testing]]))


(let [new-board (board/->Board 5 10 [] nil)
      piece {:pieces [[0 0] [0 1] [0 2] [0 3]]}
      add-piece-at (partial board/add-piece new-board piece)
      piece-pos (pa)]
  (testing "Add a piece to the board" 
    (testing "Clear of the board boundary"
      (is (= (get-in (add-piece-at 0)) [:current-piece :pos]))
      )
    (testing "At the boundary")))



(get-in {:a {:b 1}} [:a :b])
