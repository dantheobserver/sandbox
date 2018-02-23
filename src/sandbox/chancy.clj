(ns sandbox.chancy
  (:require [clojure.spec.alpha :as s]))

;; given a pool of offerings of various rarity
;; distribute to another pool based on chance to receive until they have received it
;; (e.g. lootboxes give specific items to a player, accurately calculate chance they'll receive an item)
(defn get-spec-fn [spec-def data]
  (println "spec-def: " spec-def)
  (println "data: " '({::item-name "Testing" ::item-rarity :penny-slot}))
  #_(println "valid? " (s/valid? spec-def ))
  (if (s/valid? spec-def data)
    s/conform
    s/explain-str))

(defmacro conform-ex [spec-def data]
  (let [spec-fn (get-spec-fn `~spec-def data)]
    (print "spec-fn" spec-fn)
    `(~spec-fn ~spec-def ~data)))

#_(conform-ex ::items '({::item-name "My leige" ::item-rarity :penny-slot}))


;;#


(s/def ::item-rarity #{:penny-slot :lucked-out :wild-ace})
(s/def ::item-name (s/and string? #(> (count %) 2)))
(s/def ::item (s/keys :req [::item-name ::item-rarity]))
(s/def ::items (s/coll-of ::item))

(conform-ex ::items '({::item-name "My leige" ::item-rarity :penny-slot}))

#_(conform-ex ::item-rarity :test)
#_(conform-ex ::item-rarity :wild-ace)


#_(s/conform ::chance {::item-name "Lucky Strike"
                       ::item-rarity :lucked-out})
