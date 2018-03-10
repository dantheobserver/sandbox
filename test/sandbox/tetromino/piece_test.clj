(ns sandbox.tetromino-drop-test
  (:require [sandbox.tetromino-drop :as sut]
            [clojure.test :refer :all]))
;; sandbox.tetromino-drop-tests
;; sandbox.tetromino-drop
(deftest rotation
  (let [next-coord-fn (partial sut/next-coord-cw [1 1])]
    (deftest rotate-cardinal-piece
      (is (= [2 1] (next-coord-fn [1 0])))
      (is (= [1 2] (next-coord-fn [2 1])))
      (is (= [0 1] (next-coord-fn [1 2])))
      (is (= [1 0] (next-coord-fn [0 1]))))
    (deftest rotate-diagonal-piece
      (is (= [2 0] (next-coord-fn [0 0])))
      (is (= [2 2] (next-coord-fn [2 0])))
      (is (= [0 2] (next-coord-fn [2 2])))
      (is (= [0 0] (next-coord-fn [0 2]))))))
