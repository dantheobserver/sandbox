(ns sandbox.temp-regulator)

;; Purpose
;; Set temp you want to maintain liquid to
;; Each frame, increase filament temp
;; when filament exceeds current liquid temp start ot increase liquid temp rate
;; liquid temp increases by that factors
;; filament temp will stay within margin just to apply more heat
;; after x frames, turn off, not allowing to turn on until system coold for x frames
;; translate to real/slowed time
(def initial-state {:filament-temp 0
                    :liquid-temp 70
                    :current-process :heating
                    :increase-rate 0.075}) ;deg/s

(def max-filament-temp 150)
(def liquid-increase-rate 0.05)
(def liquid-decrease-rate 0.25)
(def liquid-temp-max 150)

(defn heat-filament [{:keys [filament-temp increase-rate]}])

(defn next-filament-temp
  [{:keys [increase-rate filament-temp]
    :as state}]
  (let [next-temp (+ filament-temp increase-rate)]
    (if (>= next-temp max-filament-temp)
      (update state :filament-temp + increase-rate))))


;; Happy path
#_(= (:filament-temp (next-filament-temp {:filament-temp 0
                                          :liquid-temp 70
                                          :increase-rate 0.075}))
     0.075)

#_(= (:filament-temp (next-filament-temp {:filament-temp 70
                                          :liquid-temp 70
                                          :increase-rate 0.075}))
     70.075)

#_(= (:filament-temp (next-filament-temp {:filament-temp 150
                                          :liquid-temp 70
                                          :increase-rate 0.075}))
     150)
