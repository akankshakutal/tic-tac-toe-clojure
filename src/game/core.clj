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

(def game {:current {:moves #{} :symbol "X"} :opponent {:moves #{} :symbol "O"} :won false})

(defn valid-move? [input] (<= 1 input 9))

(defn has-won? [user-moves] (some #(every? user-moves %) win-sets))

(defn change-turn [game] (assoc game :current (:opponent game) :opponent (:current game)))

(defn update-board
  [player board]
  (reduce #(assoc %1 (dec %2) (:symbol player)) board (:moves player)))

(defn get-board
  [{:keys [current opponent]}]
  (->> (into [] (repeat 9 " "))
       (update-board current)
       (update-board opponent)))

(defn print-board
  [game]
  (->> game
       (get-board)
       (partition 3)
       (map #(clojure.string/join " | " %1))
       (clojure.string/join "\n---------\n")
       (println)))

(defn make-move
  [user-input game]
  (let [current (update (:current game) :moves conj user-input)]
    (if (has-won? (:moves current))
      (assoc game :won true :current current)
      (assoc game :current current))))

(defn play [game input]
  (-> input
      (make-move game)
      (change-turn)))

(defn print-messages [game]
  (do
    (print-board game)
    (println (str (:symbol (:current game)) " Enter Number :"))
    ))

(defn start []
  (loop [game game input (read)]
    (if (:won game)
      (println (str (:symbol (:opponent game)) " Won"))
      (do (print-messages (play game input))
          (if (valid-move? input)
            (recur (play game input) (read)))
          ))))
