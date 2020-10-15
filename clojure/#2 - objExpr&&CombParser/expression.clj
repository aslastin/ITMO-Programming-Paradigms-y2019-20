(defn d [& args] (reduce #(/ %1 (double %2)) args))
(def log-abs #(Math/log (Math/abs %)))

(defn op [app] (fn [& f] (fn [m] (apply app (map #(% m) f)))))

(def add (op +))
(def subtract (op -))
(def multiply (op *))
(def divide (op d))
(def negate subtract)

(defn constant [val] (fn [_] val))
(defn variable [v] (fn [m] (get m v)))

(def min (op clojure.core/min))
(def max (op clojure.core/max))

(defn exp-f [x] (Math/exp x))
(def exp (op exp-f))
(defn ln-f [x] (log-abs x))
(def ln (op #(log-abs %)))

(def med (op #(nth (sort %&) (quot (count %&) 2))))
(def avg (op #(d (apply + %&) (count %&))))
(def pw (op #(Math/pow %1 %2)))
(def lg (op #(d (log-abs %2) (log-abs %1))))

(defn sumexp' [args] (apply add (map exp args)))
(defn sumexp [& args] (sumexp' args))
(defn softmax [& args] (divide (exp (first args)) (sumexp' args)))

(def ops {'+      add, '- subtract, 'negate negate, '* multiply, '/ divide,
          'min    min, 'max max,
          'exp    exp, 'ln ln,
          'med    med, 'avg avg,
          'pw     pw, 'lg lg,
          'sumexp sumexp, 'softmax softmax})

(def vars {'x (variable "x"), 'y (variable "y"), 'z (variable "z")})

(defn parse [vars' constant' ops']
  #(letfn [(prse [cur]
             (cond
               (contains? vars' cur) (vars' cur)
               (number? cur) (constant' cur)
               :else (apply (ops' (first cur)) (map prse (rest cur)))))]
     (prse (read-string %))))

(def parseFunction (parse vars constant ops))
;/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)
    :else nil))

(defn field [key] (fn [this] (proto-get this key)))
(defn proto-call [this key & args] (apply (proto-get this key) this args))
(defn proto-call-static [this key & args] (apply (proto-get this key) args))
(defn method [key call] (fn [this & args] (apply call this key args)))
(defn constructor [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(def toString (method :toString proto-call))
(def toStringSuffix (method :toStringSuffix proto-call))
(def toStringInfix (method :toStringInfix proto-call))
(def evaluate (method :evaluate proto-call))
(def diff (method :diff proto-call))
(def _args (field :args))
(def _strOp (field :strOp))
(def _doEvaluate (field :doEvaluate))
(def _doDiff (method :doDiff proto-call-static))
(def _variable (field :variable))
(def _val (field :val))

(declare ONE ZERO Multiply Divide Subtract Exp Sumexp Add)

(defn toStr [sep op this] (clojure.string/join sep (map op (_args this))))

(def AbstractOperation
  {:toStringInfix  (fn [this]
                     (if (= (count (_args this)) 1)
                       (str (_strOp this) "("  ((comp toStringInfix first _args) this) ")")
                       (str "(" (toStr (str " " (_strOp this) " ") toStringInfix this) ")")))
   :toStringSuffix (fn [this] (str "(" (toStr " " toStringSuffix this) " " (_strOp this) ")"))
   :toString (fn [this] (str "(" (_strOp this) " " (toStr " " toString this) ")"))
   :evaluate (fn [this variables] (apply (_doEvaluate this) (mapv #(evaluate % variables) (_args this))))
   :diff (fn [this variable] (_doDiff this (mapv #(diff % variable) (_args this)) (_args this)))
   })

(defn createOp
  ([strOp doEvaluate doDiff]
   (constructor (fn [this & args] (assoc this :args (vec args)))
                (assoc AbstractOperation :strOp strOp :doEvaluate doEvaluate :doDiff doDiff)))
  ([strOp doEvaluate] (createOp strOp doEvaluate nil)))


(def AbstractConstant
  {:toStringInfix toString
   :toStringSuffix toString
   :toString (fn [this] (format "%.1f" (_val this)))
   :evaluate (fn [this _] (_val this))
   :diff (fn [_ _] ZERO)
   })

(def Constant (constructor #(assoc %1 :val %2) AbstractConstant))

(def ZERO (Constant 0.0))
(def ONE (Constant 1.0))
(def TWO (Constant 2.0))

(def AbstractVariable
  {:toStringInfix  toString
   :toStringSuffix toString
   :toString (fn [this] (str (_variable this)))
   :evaluate (fn [this variables] (variables (_variable this)))
   :diff (fn [this variable] (if (= variable (_variable this)) ONE ZERO))
   })

(def Variable (constructor #(assoc %1 :variable %2) AbstractVariable))

(defn appMultiply [args] (apply Multiply args))
(defn exp' [d a] (Multiply d (Exp a)))

(defn diff-add [dargs _] (apply Add dargs))
(def Add (createOp "+" + diff-add))

(defn diff-subtract [dargs _] (apply Subtract dargs))
(def Subtract (createOp "-" - diff-subtract))

(defn diff-multiply [dargs args] (first (reduce (fn [[a1 a2] [b1 b2]]
                                                  [(Add (Multiply a1 b2) (Multiply a2 b1)) (Multiply a2 b2)])
                                                (mapv vector dargs args))))
(def Multiply (createOp "*" * diff-multiply))

(defn diff-divide [[fd & rd] [farg & rargs]]
  (let [g (appMultiply rargs) g' (diff-multiply (vec rd) (vec rargs))
        f'g (Multiply fd g) fg' (Multiply farg g')]
    (Divide (Subtract f'g fg') (Multiply g g))))
(def Divide (createOp "/" d diff-divide))

(def Negate (createOp "negate" - diff-subtract))
(def Exp (createOp "exp" exp-f (fn [[darg] [arg]] (exp' darg arg))))
(def Ln (createOp "ln" ln-f (fn [[darg] [arg]] (Divide darg arg))))

(def Sum (createOp "sum" + diff-add))
(def Avg (createOp "avg" #(d (apply + %&) (count %&))
                   (fn [dargs args] (Multiply (Constant (d 1 (count args))) (diff-add dargs args)))))

(defn abs [x] (Math/abs x))
(def Abs (createOp "abs" abs (fn [_ [arg]] (Divide arg (Abs arg)))))
(def Square (createOp "square" #(* % %) (fn [[darg] [arg]] (Multiply TWO darg arg))))
(def Sqrt (createOp "sqrt" #(Math/sqrt (abs %)) (fn [[darg] [arg]] (Divide (Multiply arg darg) (Multiply TWO (Sqrt arg) (Abs arg))))))

(def eval-Sumexp #(apply + (map exp-f %&)))
(defn diff-Sumexp [dargs args] (apply Add (mapv exp' dargs args)))
(def Sumexp (createOp "sumexp" eval-Sumexp diff-Sumexp))

(def Softmax (createOp "softmax" #(d (exp-f %1) (apply eval-Sumexp %1 %&))
                       (fn [dargs args] (let [fd (first dargs) fa (first args)]
                                          (diff-divide [(exp' fd fa) (diff-Sumexp dargs args)]
                                                       [(Exp fa) (apply Sumexp args)])))))

(def Ops {'+      Add, '- Subtract, 'negate Negate, '* Multiply, '/ Divide
          'exp    Exp, 'ln Ln,
          'square Square, 'sqrt Sqrt, 'sum Sum, 'avg Avg,
          'sumexp Sumexp, 'softmax Softmax,})
(def Vars {'x (Variable "x"), 'y (Variable "y"), 'z (Variable "z")})

(def parseObject (parse Vars Constant Ops))
;/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
(defn _show [result]
  (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
                       "!"))
(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))
(defn _empty [value] (partial -return value))
(defn _char [p] (fn [[c & cs]] (if (and c (p c)) (-return c cs))))
(defn _map [f result]
  (if (-valid? result) (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
  (fn [str] (let [ar ((force a) str)]
              (if (-valid? ar) (_map (partial f (-value ar)) ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str] (let [ar ((force a) str)]
              (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))
(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (partial _map f) parser))
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value] (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps] (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps] (reduce _either p ps))
(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def Pow (createOp "**" #(Math/pow %1 %2)))
(def Log (createOp "//" #(d (ln-f %2) (ln-f %1))))

(defn createBitOp [strOp f-bit]
  (letfn [(bit-op [op arg1 arg2]
            (Double/longBitsToDouble (op (Double/doubleToLongBits arg1) (Double/doubleToLongBits arg2))))]
    (createOp strOp (partial bit-op f-bit))))

(def And (createBitOp "&" bit-and))
(def Or (createBitOp "|" bit-or))
(def Xor (createBitOp "^" bit-xor))
(def Impl (createBitOp "=>" #(bit-or (bit-not %1) %2)))
(def Iff (createBitOp "<=>" #(bit-or (bit-and %1 %2) (bit-and (bit-not %1) (bit-not %2)))))

(def OpsParsing {"+" Add, "-" Subtract, "negate" Negate, "*" Multiply, "/" Divide,
                 "**" Pow, "//" Log,
                 "&" And, "|" Or, "^" Xor,
                 "=>" Impl, "<=>" Iff})
(def VarsParsing {"x" (Variable "x"), "y" (Variable "y"), "z" (Variable "z")})

(declare *op-suffix L-start L-ground)

(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seqf concat (+seqf cons (+opt (+char "-")) (+plus *digit))
                                            (+opt (+seqf cons (+char ".") (+plus *digit)))))))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(defn *lexemes [& ops]
    (apply +or (map #(+str (apply +seq (map (comp +char str) %))) ops)))

(def *constant (+map #(Constant (double %)) *number))
(def *variable (+map #(VarsParsing %) (*lexemes "x" "y" "z")))

(def *op-suffix (+map (fn [[op & args]] (apply (OpsParsing op) args))
                      (+seqf conj (+ignore (+char "(")) *ws (+plus (+seqn 0 (+or *variable *constant (delay *op-suffix)) (+plus *space)))
                             (*lexemes "negate" "//" "**" "*" "/" "+" "-" "&" "|" "^" "=>" "<=>") *ws (+ignore (+char ")")))))
(def parseObjectSuffix (_parser (+seqn 0 *ws (+or *constant *variable *op-suffix) *ws)))


(defn left-assoc [args] (reduce (fn [arg1 [op arg2]] ((OpsParsing op) arg1 arg2)) args))
(defn right-assoc [args]
  (let [[h & t] (reverse (flatten args))]
    (reduce (fn [arg1 [op arg2]] ((OpsParsing op) arg2 arg1)) h (partition 2 t))))

(defn *binary-op [ops assoc *next-level]
  (let [*ops (apply *lexemes ops)]
    (+map assoc (+seqf cons *ws *next-level (+star (+seq *ops *next-level))))))

(defn *unary-op [ops *next-level]
  (let [*ops (apply *lexemes ops)
        process (fn [[arg & ops]] (reduce #((OpsParsing %2) %1) arg (reverse ops)))]
      (+map process (+seqf conj (+star (+seqn 0 *ws *ops)) *next-level))))

; unary ::= `func` unary

(def L-ground (+seqn 0 *ws (+or *variable *constant (+seqn 1 (+char "(") (delay L-start) (+char ")"))) *ws))
(def L9 (*unary-op ["negate"] L-ground))
;[(right "**" "//")
; (left "*" "/")]
(def L8 (*binary-op ["**" "//"] right-assoc L9))
(def L7 (*binary-op ["*" "/"] left-assoc L8))
(def L6 (*binary-op ["+" "-"] left-assoc L7))
(def L5 (*binary-op ["&"] left-assoc L6))
(def L4 (*binary-op ["|"] left-assoc L5))
(def L3 (*binary-op ["^"] left-assoc L4))
(def L2 (*binary-op ["=>"] right-assoc L3))
(def L-start (*binary-op ["<=>"] left-assoc L2))

(def parseObjectInfix (_parser L-start))







