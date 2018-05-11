(ns sandbox.graphics.stars-test
  (:require [sandbox.graphics.stars :as stars]
            [clojure.test :refer (deftest is are testing)]))

(deftest next-pos
  (is (= {:pos [0 0] :vec [0 0]}
         (stars/next-pos {:pos [0 0] :vec [0 0]})))
  (is (= {:pos [0 1] :vec [0 1]}
         (stars/next-pos {:pos [0 0] :vec [0 1]})))
  (is (= {:pos [1 0] :vec [1 0]}
         (stars/next-pos {:pos [0 0] :vec [1 0]})))
  (is (= {:pos [1 1] :vec [1 1]}
         (stars/next-pos {:pos [0 0] :vec [1 1]}))))


(deftest boundary-hit?
  (is (= :TB (stars/boundary-hit? {:rad 1 :pos [2 1] :vec [0 -1]} [10 10])) "top boundary hit")
  (is (= :TB (stars/boundary-hit? {:rad 1 :pos [1 9] :vec [0 1]} [10 10])) "bottom boundary hit")
  (is (= :LR (stars/boundary-hit? {:rad 1 :pos [9 0] :vec [1 0]} [10 10])) "right boundary hit")
  (is (= :LR (stars/boundary-hit? {:rad 1 :pos [1 0] :vec [-1 0]} [10 10])) "left boundary hit"))

(deftest move-star
  (let [boundary [10 10]
        starting-pos [5 5]])
  (testing "moving within the boundary")
  (testing "hitting the boundary"))
