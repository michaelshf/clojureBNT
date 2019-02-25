(ns clojureBNT.ch3
  (:require [clojure.string :as string]))

;; syntax
; forms
"a string"
["a" "vector" "of" "strings"]

; operators
(comment
  (operator operand1 operand2 ... operandn))
(+ 1 2 3)
(str "It was panda " "in library " "with dust buster")

; control flow
; if
(comment
  (if boolean-form
    then-form
    optional-else-form))
(if true
  "By Zeus's hammer!"
  "By aquaman's trident!")
(if false
  "By Zeus's hammer!"
  "By aquaman's trident!")
(if false
  "By odin's elbow!")

; do
(if true
  (do (println "do success!")
      "By Zeus's hammer!")
  (do (println "do Failure!")
      "By Aquaman's Trident!"))

; when
(when true
  (println "when success")
  "abra cadabra")

; nil, true, false, truthiness, equality, boolean exps
(nil? 1)
(nil? nil)

(if "bears eat beets"
  "Battlestar Galactica")
(if nil
  "This won't be result"
  "nil is falsey")

(= 1 1)
(= nil nil)
(= 1 2)

(or false nil :large_I_mean_venti :why_cant_I_say_large)
(or (= 0 1) (= "yes" "no"))
(or nil)

(and :free_wifi :hot_coffee)
(and :feelin_super_cool nil false)

;; Naming Values with Def
; def
(def failed-protagonist-names
  ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])
failed-protagonist-names

(defn error-message
  [severity]
  (str "Oh god, it's a disaster, we're "
       (if (= severity :mild)
         "mildly inconvenienced"
         "doooooomed")))

(error-message :mild)
(error-message :anything)

;; Data structures
; numbers
93
1.2
1/5

; strings
"Lord voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"

(def name "Chewbacca")
(str "\"Ugglglg\" - " name)

;maps
{}
{:first-name "charlie"
 :last-name "McFishwich"}

{"string-key" +}

{:name {:first "John"
        :middle "Jacob"
        :last "Jingleheimerschmit"}}

(hash-map :a 1 :b 2)

(get {:a 0 :b 1} :b)
(get {:a 0 :b {:c "ho hum"}} :b)
(get {:a 0 :b 1} :c)
(get {:a 0 :b 1} :c "unicorns?")

(get-in {:a 0 :b {:c "ho-hum"}} [:b :c])

({:name "The human coffeepot"} :name)

; keywords
:a
:rumbplestilsken
:34
:_?

(:a {:a 1 :b 2 :c 3})

(get {:a 1 :b 2 :c 3} :a)

(:d {:a 1 :b 2 :c 3} "no gnome knows homes like noah knows")

; vectors
[3 2 1]

(get [3 2 1] 0)
(get ["a" {:name "pubsley winterbottom"} "c"] 1)
(vector "creepy" "full" "moon")

(conj [1 2 3] 4)

; lists
'(1 2 3 4)

(nth '(:a :b :c) 0)
(nth '(:a :b :c) 2)

(list 1 "two" {3 4})
(conj '(1 2 3) 4)

; sets
#{"kurt vonnegut" 20 :icicle}

(hash-set 1 1 2 2)

(conj #{:a :b} :b)

(set [3 3 3 4 4])

(contains? #{:a :b} :a)
(contains? #{:a :b} 3)
(contains? #{nil} nil)

(:a #{:a :b})

(get #{:a :b} :a)
(get #{:a nil} nil)
(get #{:a :b} "kur vonnegut")


;; simplicity
; better to have 100 functions on one data struct
; than 10 functions on 10 structures

;; functions
; calling functions
(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])

((or + -) 1 2 3)
((and (= 1 1) +) 1 2 3)
((first [+ 0]) 1 2 3)

(comment
  "incorrect func calls"
  (1 2 3 4)
  ("test" 1 2 3))

(inc 1.1)
(map inc [0 1 2 3])

(+ (inc 199) (/ 100 (- 7 2)))

;; function, macros special forms
(comment
  (if good-mood
    (tweet walking-on-sunshine-lyrics)
    (tweet mopey-country-song-lyrics))
  )

; defining functions
(defn too-enthusiastic
  "return a cheer that is too much"
  [name]
  (str "OMG " name " you are the best"))

(too-enthusiastic "Zelda")

; docstring:
(comment
  docstring returns when calling:
  (doc fn-name))

; parameters and arity
(defn no-params
  []
  "I take no parameters")

(defn one-param
  [x]
  (str "I take one param: " x))

(defn two-params
  [x y]
  (str "smushing too params" x y))

(comment (defn multi-arity
           ([first-arg second-arg third-arg]
            (do-things first-arg second-arg third-arg))
           ([first-arg second-arg]
            (do-things first-arg second-arg))
           ([first-arg]
            (do-things first-arg))))

(defn x-chop
  "describr the kind of chop your using"
  ([name chop-type]
   (str "I " chop-type " chop " name))
  ([name]
   (x-chop name "karate")))

(x-chop "kanye west" "slap")
(x-chop "kanye east")

(defn weird-arity
  ([]
   "docstring with zero-arity")
  ([number]
   (inc number)))

(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Biller" "Anne-Marie" "The Incredible Hulk")

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things:"
       (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")

; destructuring
(defn my-first
  [[first-thing]]
  first-thing)

(my-first ["oven" "bike" "war-axe"])

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need them: "
                (clojure.string/join "," unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "treasure lng: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})

; function body
(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")

(illustrative-function)

(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! what a big number!"
    "That number's OK, I guess"))

(number-comment 5)
(number-comment 7)

; all functions are created equal

; Anonymous Functions
(comment (fn [param-list]
           function body))

(map (fn [name] (str "hi, " name))
     ["darth vader" "mr. magoo"])

((fn [x] (* x 3)) 8)

(def my-special-multiplier (fn [x] (* x 3)))
(my-special-multiplier 12)

#(* % 3)

(#(* % 3) 8)

(map #(str "Hi, " %)
     ["Darth vader" "mr. magoo"])

(* 8 3)
#(* % 3)

(#(str %1 " and " %2) "cornbread" "butter beans")
(#(identity %&) 1 "blarg" :yip)

; returning functions
(defn inc-maker
  [inc-by]
  #(+ % inc-by))
(def inc3 (inc-maker 3))
(inc3 7)

; Shire's Next Top Model
 (def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "expects seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

; let
(let [x 3]
  x)

(def dalmatian-list
  ["pongo" "perdita" "puppy 1" "puppy 2"])
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)

(def x 0)
(let [x 1]
  x)
x
(def x 0)
(let [x (inc x)]
  x)

(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])

; loop
(loop [iteration 0]
  (println (str "interation " iteration))
  (if (> iteration 3)
    (println "Goodbye")
    (recur (inc iteration))))

(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "goodbye")
     (recursive-printer (inc iteration)))))
(recursive-printer)

; regular expressions
#"regular-expression"
(re-find #"^left-" "left-eye")
(re-find #"^left-" "cleft-chin")
(re-find #"^left-" "other right")

; better symmetrizer with reduce:
(reduce + [1 2 3])
(+ (+ 1 2) 3)
(reduce + 10 [1 2 3])

(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn better-symmetrize-body-parts
  "expects seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

; hobbit violence:
(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)
(hit asym-hobbit-body-parts)

;; Exercises:
; 1. Use str, vector, list, hash-map, hash-set
(str "hey " "there")
(vector 1 2 3)
(list 1 2 3)
(hash-map :a 1 :b 2 :c 3)
(hash-set 1 1 2 3 3)
; 2. Write a function that takes a number and adds 100
(defn hundred-adder
  [number]
  (+ number 100))
(hundred-adder 5)
; 3. Write a function dec-maker that works like inc-maker but with dec
(defn dec-maker
  [dec-val]
  #(- % dec-val))
((dec-maker 5) 86)
; 4. Write a funciton mapset that works like map except returns a set
(comment
  (mapset inc [1 1 2 2])
  => #{2 3})
(defn mapset
  [coll]
  (set (map inc coll)))
(mapset [1 1 2 2])
; 5. Create a function similar to sym-body-parts, but for 5-parted radial
(defn matching-radial
  [part]
  [{:name (clojure.string/replace (:name part) #"^left-" "middle-")
    :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^left-" "top-")
    :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)}
   {:name (clojure.string/replace (:name part) #"^left-" "bottom-")
   :size (:size part)}])

(matching-radial {:name "left-hand" :size 3})

(defn radialize-body-parts
  "expects seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      (distinct (flatten final-body-parts))
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-radial part)])))))))

(clojure.pprint/pprint (radialize-body-parts asym-hobbit-body-parts))

; 6. Create a function that generalizes sym-body-parts
(defn generlize-body-parts
  [asym-body-parts gen-count]
  )
