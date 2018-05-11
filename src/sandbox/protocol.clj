(ns sandbox.protocol)

(defprotocol ToTimestamp
  (to-timestamp [x]))

(extend-protocol ToTimestamp
  Long
  (to-timestamp [l] l)
  java.util.Date
  (to-timestamp [date] (.getTime date)))

(defn to-date [x]
  (java.util.Date.
   (to-timestamp x)))


;; TreeNode
(defprotocol FIXO
  (fixo-push [fixo value])
  (fixo-peek [v])
  (fixo-pop [v]))

(deftype TreeNode [val l r]
  FIXO
  (fixo-push [_ v]
    (if (< v val)
      (TreeNode. val (fixo-push l v) r)
      (TreeNode. val l (fixo-push r v))))
  (fixo-peek [_]
    (if l
      (fixo-peek l)
      val))
  (fixo-pop [_]
    (if l
      (TreeNode. val (fixo-pop l) r)
      r)))

(extend-protocol FIXO
  nil
  (fixo-push [_ v]
    (TreeNode. v nil nil)))

(-> (TreeNode. 1 nil nil)
    (fixo-push 2))
