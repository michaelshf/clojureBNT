(ns clojureBNT.ch4
  (:require [clojure.string :as string]))

;; Core Functions In Depth
; Programming to Abstractions
; - abstractions~ named collections of operations
; - ie: map doesn't care about collection, it cares about operation on collection

; Treating Lists, Vectors, Sets, and Maps as Seqs
; - Seqs are linearly ordered collections of elements
; - constrasts with unordered or graph ordered collections
; - operations that take advantage of this are said to "implement" seq abstraction
; - example operations include...

(defn titleize
  [topic]
  (str topic " for the brave and true"))

(map titleize ["Hamsters" "Ragnarok"])
(map titleize '("Empathy" "Decorating"))
(map titleize #{"Elobws" "soap carving"})
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})

; first, rest and cons -> concepts in JS-like
#_(Linked Lists in generic terms
          var node3 = {
                       value "last"
                       next null}
          var node2 = {
                       value "middle"
                       next node3}
          var node1 = {
                       value "first"
                       next node2}

          var first = function (array) {
                                        return array[0]
                                        }
          var rest = function (array) {
                                       var sliced = array.slice (1, array.length)
                                       if (sliced.length==0) {
                                                              return null
                                                              }
                                       else {
                                             return sliced;
                                             }
                                       }
          var cons = function (newValue, array) {
                                                 return [newValue].concat (array)
                                                 }

          (first node1 => "first"
                 first (rest (node1)) => "middle"
                 first (rest (rest (node1))) => "last"
                 var node0 = cons ("new first", node1)
                 first (node0) => "new first"
                 first (rest (node0)) => "first")

          #(var map = function (list, transform) {
                                                  if (list == null) {
                                                                     return null}
                                                  else {
                                                        return cons (transform (first (list)), map (rest (list), transform))
                                                        }
                                                  }))

; abstraction through indirection
; - indirection: mechanisms to employ multiple related meanings
; - - polymorphism: mechanism to disbatch to different function bodies based on arg type
; - - before map/first/rest/cons: clojure calls seq on the collection
(seq '(1 2 3))
(seq [1 2 3])
(seq #{ 1 2 3})

(seq {:name "Bill Compton" :occupation "dead mopey guy"})
(into {} (seq {:a 1 :b 2 :c 3}))

;; Seq Function Examples
; map
(map inc [1 2 3])
(map str ["a" "b" "c"] ["A" "B" "C"])
(list (str "a" "A") (str "b" "B") (str "c" "C"))

(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})
(map unify-diet-data human-consumption critter-consumption)

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
(stats [80 1 44 13 6])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
(map :real identities)

; reduce
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
(assoc (assoc {} :max (inc 30))
       :min (inc 10))

 (reduce (fn [new-map [key val]]
           (if (> val 4)
             (assoc new-map key val)
             new-map))
         {}
         {:human 4.1
          :critter 3.9})

; take, drop, take-while, drop-while
(take 3 [1 2 3 4 5 6 7 8 9 10])
(drop 3 [1 2 3 4 5 6 7 8 9 10])

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])
(take-while #(< (:month %) 3) food-journal)
(drop-while #(< (:month %) 3) food-journal)
(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))

; filter and some
(filter #(< (:human %) 5) food-journal)
(filter #(< (:month %) 3) food-journal)
(some #(> (:critter %) 5) food-journal)
(some #(> (:critter %) 3) food-journal)
(some #(and (> (:critter %) 3) %) food-journal)

; sort and sort-by
(sort [1 3 2])
(sort-by count ["aaa" "c" "bb"])

; concat
(concat [1 2] [2 4])

;; Lazy Seqs
; demonstrating lazy seq efficiency
(def vampire-database
  {0 {:makes-blood-puns? false :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [ssn]
  (Thread/sleep 1000)
  (get vampire-database ssn))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [ssns]
  (first (filter vampire?
                 (map vampire-related-details ssns))))

(time (vampire-related-details 0))
(time (def mapped-details (map vampire-related-details (range 0 1000000))))
(time (first mapped-details))
(time (first mapped-details))
(time (identify-vampire (range 0 1000000)))

; infinite sequences
(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
(take 10 (even-numbers))
(cons 0 '(2 4 6))

;; Collection Abstraction
; - Similar to seq, also obeyed by most data structures (sets, maps, lists, vectors)
; - Collection is principle of apply operation to data structure as a whole
(empty? [])
(empty? ["no!"])

; into
(map identity {:sunlight-reaction "Glitter!"})
(into {} (map identity {:sunlight-reaction "Glitter!"}))

(map identity [:garlic :sesame-oil :fried-eggs])
(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
(into #{} (map identity [:garlic :garlic]))
(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])

; conj
(conj [0] [1])
(into [0] [1])
(conj [0] 1)
(conj [0] 1 2 3 4)
(conj {:time "midnight"} [:place "ye olde cemetarium"])
(defn my-conj
  [target & additions]
  (into target additions))
(my-conj [0] 1 2 3)

;; Function Functions
; apply
(max 0 1 2)
(max [0 1 2])
(apply max [1 2 3])

(defn my-into
  [target additions]
  (apply conj target additions))
(my-into [0]  [1 2 3])

; partial
(def add10 (partial + 10))
(add10 3)
(add10 5)

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))
(add-missing-elements "fire")

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))
(def add20 (my-partial + 20))
(add20 43)
(fn [& more-args]
  (apply + (into [20] more-args)))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))
(warn "red light AHEAD")

; complement
(defn indentify-humans
  [ssns]
  (filter #(not (vampire? %))
          (map vampire-related-details ssns)))

(def not-vampire? (complement vampire?))
(defn identify-humans
  [ssns]
  (filter not-vampire? (map vampire-related-details ssns)))

(comment
  (defn my-complement
    [fun]
    (fn [& args]
      (not (apply fun args)))))

(def my-pos? (complement neg?))
(my-pos? 1)
(my-pos? -1)

;; Vampire Data Analysis Program for FWPD
; - See fwpd app

;; Exercises
; 1. Turn the result of your glitter filter into a list of names.
; - see fwpd
; 2. Write a function, append, which will append a new suspect to your list of suspects.
(defn append
  "adds a suspect to a list of suspects"
  [new-suspect suspect-list]
  (into suspect-list new-suspect))
; => new suspects should look like: {# {:makes-blood-puns? boolean, :has-pulse? boolean, :name "string"}}

; 3. Write a function, validate, which will check name and glitter-index when appending.
;   - should take a map of keywards and record to be validated
(defn validate
  [fn-keywords record]
  (= (keys record) (keys fn-keywords)))

; 4. Write a function that takes a list of maps and converts to a csv string using string/join
(defn convert-csv
  [map-list]
  (let [stringed-list (clojure.string/join "," (map identity map-list))]
       (clojure.string/replace (clojure.string/replace (stringed-list) "{" "") "}" "")))

