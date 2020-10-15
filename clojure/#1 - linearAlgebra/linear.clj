(defn dim-matrix [dim_eq m]
  (letfn [(wrap [a b] (into [(count a)] (apply concat b)))
          (build [m'] {:pre [(or (vector? m') (number? m'))]}
            (if (vector? m') (wrap m' (mapv build m')) [0]))]
    (if (vector? m)
        (let [m' (mapv build m) r (wrap m m')]
          (if dim_eq
            (if (apply = m')
              (into [] (take-while #(not (zero? %)) r))
              (throw (AssertionError. "Dimensions have different sizes")))
            r)))))

(defn buildMatrix [dim_eq dim_c dim_sz] {:dim_eq dim_eq :dim_c dim_c :dim_sz dim_sz})
(def v (buildMatrix true #{1} #{}))
(def m (buildMatrix true #{2} #{}))
(def c (buildMatrix true #{3} #{}))
(def t (buildMatrix true #{} #{}))
(def s (buildMatrix false #{} #{}))

(defn check-matrix [m' & ms]
  (let [dim (dim-matrix (:dim_eq m') (first ms))]
    (and (or (empty? (:dim_c m')) (contains? (:dim_c m') (count dim)))
         (or (empty? (:dim_sz m')) (every? #(contains? (:dim_sz m') %) dim))
         (every? #(= (dim-matrix (:dim_eq m') %) dim) (rest ms)))))

(def check-v (partial check-matrix v))
(def check-m (partial check-matrix m))

(defn l-op [m' op]
  (letfn [(rec-op [& args]
            (if (number? (first args))
              (apply op args)
              (apply mapv rec-op args)))]
    (fn [& args]
      {:pre [(apply check-matrix m' args)]
       :post [(check-matrix m' %)]}
      (apply rec-op args))))

(defn b [f]
  (fn [& args]
    (let [wrapv #(into [] %)
          vargs (wrapv args)
          dims (mapv (partial dim-matrix true) vargs)
          dims_c (mapv #(count %) dims)
          mx (reduce-kv #(if (<= (:val %1) %3) (assoc %1 :val %3 :i %2) %1) {:val -1 :i 0} dims_c)
          p #(let [x (split-at (- (:val mx) %2) (dims (:i mx)))]
               (if (= (second x) (wrapv (dims %1)))
                 (reduce (fn [a b] (wrapv (repeat b a))) (vargs %1) ((comp reverse first) x))
                 (throw (AssertionError. "Tensors are incompatible"))))]
      (apply f (map-indexed p dims_c)))))

(defn d [& args] (double (apply / args)))
(defn *s [args]
  {:pre [(every? #(number? %) args)]}
  (let [sc (apply * args)] #(* sc %)))

(def v+ (l-op v +))
(def v- (l-op v -))
(def v* (l-op v *))
(def vd (l-op v d))
(defn v*s [v' & args] ((l-op v (*s args)) v'))

(defn scalar [& args] {:post [(number? %)]} (apply + (apply v* args)))
(defn vect [& args]
  {:pre [(apply check-matrix (assoc v :dim_sz #{3}) args)]
   :post [(check-matrix (assoc v :dim_sz #{3}) %)]}
    (reduce #(let [[x1 y1 z1] %1 [x2 y2 z2] %2
                    det (fn [[a b c d]] (- (* a d) (* c b)))]
                (mapv det [[y1 y2 z1 z2] [(- x1) x2 (- z1) z2] [x1 x2 y1 y2]])) args))

(def m+ (l-op m +))
(def m- (l-op m -))
(def m* (l-op m *))
(def md (l-op m d))
(defn m*s [m' & args] ((l-op m (*s args)) m'))

(defn transpose [m']
  {:pre [(check-m m')]
   :post [(check-m %) (= (count %) (count (first m'))) (= (count (first %)) (count m'))]}
  (apply mapv vector m'))

(defn m*v [m' v']
  {:pre [(check-m m') (= (count (first m')) (count v'))]
   :post [(check-v %) (= (count %) (count m'))]}
    (mapv #(scalar v' %) m'))

(defn m*m [m' & args]
  (reduce (fn [m1 m2] (transpose (mapv #(m*v m1 %) (transpose m2)))) m' args))

(def s+ (l-op s +))
(def s- (l-op s -))
(def s* (l-op s *))
(def sd (l-op s d))

(def c+ (l-op c +))
(def c- (l-op c -))
(def c* (l-op c *))
(def cd (l-op c d))

(def t+ (l-op t +))
(def t- (l-op t -))
(def t* (l-op t *))
(def td (l-op t d))

(def b+ (b t+))
(def b- (b t-))
(def b* (b t*))
(def bd (b td))
