(ns game.core)

(def win-sets
  #{[1 2 3],
    [4 5 6],
    [7 8 9],
    [1 4 7],
    [2 5 8],
    [3 6 9],
    [1 5 9],
    [3 5 7]})

(def game {:current #{} :opponent #{} :won false})

(defn valid-move?
  [input]
  (<= 1 input 9))

(defn has-won?
  [user-moves]
  (some #(every? user-moves %) win-sets))

(defn make-move
  [user-input {:keys [current opponent]}]
  (let [current (conj current user-input)]
    (if (has-won? current)
      (assoc game :current opponent :opponent current :won true)
      (assoc game :current opponent :opponent current))))

(defn get-user-input
  []
  (loop [input (read) game game]
    (if (has-won? (:opponent (make-move input game)))
      (println "You Won")
      (do
        (println "Enter Number :")
        (if (valid-move? input)
          (recur (read) (make-move input game))
          (println "Invalid Move")
          )
        ))))