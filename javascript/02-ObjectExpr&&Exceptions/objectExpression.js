"use strict";

const AbstractOperation = {
    toString() {
        return this.args.map(el => el.toString()).join(' ') + " " + this.strOp;
    },
    evaluate(...variables) {
        return this.doEvaluate(...this.args.map(el => el.evaluate(...variables)));
    },
    diff(variable) {
        return this.doDiff(this.args.map(el => el.diff(variable)), this.args);
    },
    prefix() {
        return '(' + this.strOp + ' ' + this.args.map(el => el.prefix()).join(' ') + ')';
    },
    postfix() {
        return '(' + this.args.map(el => el.postfix()).join(' ') + ' ' + this.strOp + ')';
    }
};

function createOperation(strOp, doEvaluate, doDiff) {
    function Constructor(...args) {
        this.args = args;
    }
    Constructor.prototype = Object.create(AbstractOperation);
    Constructor.prototype.strOp = strOp;
    Constructor.prototype.doEvaluate = doEvaluate;
    Constructor.prototype.doDiff = doDiff;
    Constructor.prototype.constructor = Constructor;
    return Constructor;
}

const AbstractConst = {
    toString() {
        return this.value.toString();
    },
    diff() {
        return ZERO;
    },
    evaluate() {
        return +this.value;
    },
    prefix() {
        return this.toString();
    },
    postfix() {
        return this.prefix();
    }
};


const AbstractVariable = {
    getIndexOfVariable : {
        'x' : 0,
        'y' : 1,
        'z' : 2
    },
    toString() {
        return this.variable.toString();
    },
    diff(variable) {
        if (this.variable === variable) {
            return ONE;
        } else {
            return ZERO;
        }
    },
    evaluate(...variables) {
        return variables[AbstractVariable.getIndexOfVariable[this.variable] || 0];
    },
    prefix : AbstractConst.prefix,
    postfix : AbstractConst.postfix
};


function Const(value) { this.value = value }
Const.prototype = Object.create(AbstractConst);


function Variable(variable) { this.variable = variable }
Variable.prototype = Object.create(AbstractVariable);


const ZERO = new Const(0);
const ONE = new Const(1);
const TWO = new Const(2);
const NEG_ONE_SECOND = new Const(-1/2);
const THREE = new Const(3);


const Add = createOperation('+', (a, b) => a + b, ([dx, dy]) => new Add(dx, dy));
const Subtract = createOperation('-', (a, b) => a - b, ([dx, dy]) => new Subtract(dx, dy));
const Multiply = createOperation('*', (a, b) => a * b, ([dx, dy], [x, y]) => new Add(new Multiply(dx, y), new Multiply(x, dy)));
const Divide = createOperation('/', (a, b) => a / b,
    ([dx, dy], [x, y]) => new Divide(new Subtract(new Multiply(dx, y), new Multiply(dy, x)), new Multiply(y, y)));
const Negate = createOperation('negate', a => -a, ([dx]) => new Negate(dx));
const Exp = createOperation('exp', Math.exp, ([dx], [x]) => new Multiply(new Exp(x), dx));
const Gauss = createOperation('gauss', (a, b, c, x) => a * Math.exp(-0.5 * ((x - b) * (x - b)) / (c * c)),
    ([da, db, dc, dx], [a, b, c, x]) => {
            let dif = new Subtract(x, b);
            return new Multiply(
            new Exp(new Multiply(NEG_ONE_SECOND, new Power(new Divide(dif, c), TWO))),
            new Subtract(da, new Multiply(a,
                new Divide(
                    new Multiply(dif,
                        new Subtract(new Multiply(c, new Subtract(dx, db)), new Multiply(dc, dif))
                    ),
                    new Power(c, THREE)
                )
                )
            )
        )
    }
);

const Min3 = createOperation('min3', Math.min);
const Max5 = createOperation('max5', Math.max);
const Ln = createOperation('ln', x => Math.log(Math.abs(x)), ([dx], [x]) => new Divide(dx, x));
const Power = createOperation('pow', Math.pow,
    ([dx, dy], [x, y]) =>  {
        let lnx = new Ln(x);
        return new Multiply(
            new Exp(
                new Multiply(y, lnx)
            ),
            new Add(
                new Multiply(dy, lnx),
                new Multiply(y, new Divide(dx, x))
            )
        )
    }
);
const Log = createOperation('log', (x, y) => Math.log2(Math.abs(y))/Math.log2(Math.abs(x)),
    ([dx, dy], [x, y]) => {
        let lnx = new Ln(x);
        return new Divide(
            new Subtract(
                new Multiply(new Divide(dy, y), lnx), new Multiply(new Divide(dx, x), new Ln(y))
            ), new Multiply(lnx, lnx)
        )
    }
);
const Sinh = createOperation('sinh', Math.sinh,
    ([dx], [x]) => new Divide(new Multiply(new Add(new Exp(x), new Exp(new Negate(x))), dx), TWO)
);
const Cosh = createOperation('cosh', Math.cosh,
    ([dx], [x]) => new Divide(new Multiply(new Subtract(new Exp(x), new Exp(new Negate(x))), dx), TWO)
);
const ArcTan = createOperation('atan', Math.atan,
    ([dx], [x]) => new Divide(dx, new Add(ONE, new Multiply(x, x)))
);
const Sum = createOperation('sum', (...ops) => ops.reduce((sum, el) => sum + el, 0),
    (d) => d.reduce((res, el) => new Add(res, el), ZERO)
);
const Avg = createOperation('avg', (...ops) => Sum.prototype.doEvaluate(...ops) / ops.length,
    (d) => new Multiply(new Const(1 / d.length), Sum.prototype.doDiff(d))
);
const Sumexp = createOperation('sumexp', (...ops) => ops.reduce((sum, el) => sum + Math.exp(el), 0),
    (d, v) => v.reduce((res, el, index) => new Add(res, new Multiply(new Exp(el), d[index])), ZERO)
);
const Softmax = createOperation('softmax', (...ops) => Math.exp(ops[0]) / Sumexp.prototype.doEvaluate(...ops),
    (d, v) => Divide.prototype.doDiff([Exp.prototype.doDiff([d[0]], [v[0]]), Sumexp.prototype.doDiff(d, v)], [new Exp(v[0]), new Sumexp(...v)])
);
const Mean = createOperation('mean', Avg.prototype.doEvaluate, Avg.prototype.doDiff);
const Var = createOperation('var', (...ops) => {
    let res = Avg.prototype.doEvaluate(...ops);
    return Avg.prototype.doEvaluate(...ops.map(el => (el - res) * (el - res)));
}, (d, v) => {
        let p = new Const(1 / v.length);
        return new Multiply(TWO, new Multiply(p, new Subtract(new Sum(...d.map((el, index) => new Multiply(el, v[index]))),
               new Multiply(new Multiply(p, new Sum(...v)), new Sum(...d)))))
    }
);



const OpExpr = {
    "+" : [Add, 2],
    "-" : [Subtract, 2],
    "*" : [Multiply, 2],
    "/" : [Divide, 2],
    "negate" : [Negate, 1],
    "max5" : [Max5, 5],
    "min3" : [Min3, 3],
    "pow" : [Power, 2],
    "log" : [Log, 2],
    "sinh" : [Sinh, 1],
    "cosh" : [Cosh, 1],
    "gauss" : [Gauss, 4],
    "exp" : [Exp, 1],
    "atan" : [ArcTan, 1],
    "sum" : [Sum, Infinity],
    "avg" : [Avg, Infinity],
    "sumexp" : [Sumexp, Infinity],
    "softmax" : [Softmax, Infinity],
    "mean" : [Mean, Infinity],
    "var" : [Var, Infinity]
};

const CnstVarExpr = {
    "x" : new Variable("x"),
    "y" : new Variable("y"),
    "z" : new Variable("z"),
};

function parse(input) {
    return input.split(/[ \t]+/).filter(s => s.length > 0).reduce((storage, lexeme) => {
        if (OpExpr.hasOwnProperty(lexeme)) {
            storage.push(new OpExpr[lexeme][0](...storage.splice(storage.length - OpExpr[lexeme][1], OpExpr[lexeme][1])));
        } else if (CnstVarExpr.hasOwnProperty(lexeme)) {
            storage.push(CnstVarExpr[lexeme]);
        } else {
            storage.push(new Const(+lexeme));
        }
        return storage;
    }, []).pop();
}


function Token(val, pos) {
    this.val = val;
    this.pos = pos;
}
Token.prototype = Object.create(null);

function pushBracket(tokens, index, char, isPrefix) {
    if (!isPrefix) {
        if (char === '(') {
            tokens.push(new Token(')', index));
        } else {
            tokens.push(new Token('(', index));
        }
    } else {
        tokens.push(new Token(char, index));
    }
}

function getTokens(input, isPrefix) {
    let tokens = [], start = -1, end;
    input.split('').forEach((char, index) => {
        if (start === -1) {
            if (char !== ' ' && char !== '\t') {
                if (char === '(' || char === ')') {
                    pushBracket(tokens, index, char, isPrefix);
                } else {
                    start = index;
                    end = start + 1;
                }
            }
        } else {
            if (char === ' ' || char === '\t' || char === ')' || char === '(') {
                tokens.push(new Token(input.substring(start, end), start));
                start = -1;
                if (char === ')' || char === '(') {
                    pushBracket(tokens, index, char, isPrefix);
                }
            } else {
                end++;
            }
        }
    });
    if (start !== -1) {
        tokens.push(new Token(input.substring(start), start));
    }
    if (!isPrefix) {
        tokens.reverse();
    }
    return tokens;
}

function parsePrefixPostfix(input, isPrefix) {
    let tokens = getTokens(input, isPrefix);
    let pos = 0;
    function parse() {
        if (tokens[pos].val === '(') {
            pos++;
            if (pos < tokens.length && OpExpr.hasOwnProperty(tokens[pos].val)) {
                let storage = [], count = 0, currPos = pos++;
                while (pos < tokens.length && tokens[pos].val !== ')') {
                    storage.push(parse());
                    count++;
                }
                if (count === OpExpr[tokens[currPos].val][1] || OpExpr[tokens[currPos].val][1] === Infinity) {
                    if (pos !== tokens.length && tokens[pos++].val === ')') {
                        if (!isPrefix) {
                            storage.reverse();
                        }
                        return new OpExpr[tokens[currPos].val][0](...storage);
                    }
                    throwRightBracketParseError(pos, tokens, input, isPrefix);
                }
                throwOpArgParseError(currPos, count, pos, tokens, input, isPrefix);
            }
            throwLeftBracketParseError(pos, tokens, input, isPrefix);
        }
        if (CnstVarExpr.hasOwnProperty(tokens[pos].val)) {
            return CnstVarExpr[tokens[pos++].val];
        }
        if (!Number.isNaN(Number(tokens[pos].val))) {
            return new Const(+tokens[pos++].val);
        }
        throwIncorrectTokenParseError(pos, tokens, input, isPrefix);
    }
    if (tokens.length === 0) {
        throw new ParseError("Empty input");
    }
    let res = parse();
    if (pos === tokens.length) {
        return res;
    }
    throwTooManySymbolsParseError(pos, tokens, input, isPrefix);
}

function parsePrefix(input) {
    return parsePrefixPostfix(input, true);
}

function parsePostfix(input) {
    return parsePrefixPostfix(input, false);
}

function ParseError(message, pos, tokens, input, isPrefix,
                     moveLeft = 30, moveRight = 30) {
    let errorLeftBound, errorRightBound, info = [message];
    if (tokens !== undefined  && isPrefix !== undefined && pos !== undefined) {
        errorLeftBound = pos === tokens.length ? input.length : tokens[pos].pos;
        if (isPrefix) {
            errorRightBound = pos + 1 < tokens.length ? tokens[pos + 1].pos - 1 : input.length;
        } else {
            errorRightBound = pos - 1 >= 0 ? tokens[pos - 1].pos - 1 : input.length
        }
        if (errorLeftBound >= 0) {
            info.push("ErrorPos: " + (errorLeftBound + 1));
            if (input !== undefined && errorLeftBound <= errorRightBound && errorRightBound < input.length) {
                let str;
                if (errorLeftBound - moveLeft > 0) {
                    str = '...' + input.slice(errorLeftBound - moveLeft, errorLeftBound);
                } else {
                    str = input.slice(0, errorLeftBound);
                }
                str += " <ERROR> ";
                if (errorRightBound + moveRight < input.length) {
                    str += input.slice(errorRightBound + 1, errorRightBound + moveRight) + '...';
                } else {
                    str += input.slice(errorRightBound + 1, input.length);
                }
                info.push(str);
            }
        }
    }
    this.message = info.join("       ");
}
ParseError.prototype = Object.create(Error.prototype);
ParseError.prototype.name = "ParseError";
ParseError.prototype.constructor = ParseError;


function createParseError(name) {
    function Constructor(...info) {
        ParseError.call(this, ...info);
    }
    Constructor.prototype = Object.create(ParseError.prototype);
    Constructor.prototype.name = name.toString();
    Constructor.prototype.constructor = Constructor;
    return Constructor;
}
const OpArgParseError = createParseError("OpArgParseError");
const TokenParseError = createParseError("TokenParseError");
const BracketParseError = createParseError("BracketParseError");


function throwRightBracketParseError(pos, tokens, input, isPrefix) {
    let message = isPrefix ? `Expected right bracket after operation args.${pos === tokens.length ? '' : ` Found ${tokens[pos].val}`}` :
                 `Expected left bracket. Operation must be in the brackets!${pos === tokens.length ? '' : ` Found ${tokens[pos].val}`}`;
    throw new BracketParseError(message, pos, tokens, input, isPrefix);
}

function throwOpArgParseError(currPos, count, pos, tokens, input, isPrefix) {
    throw new OpArgParseError(`Error during parsing of Operation[${tokens[currPos].val}]. Expected ${OpExpr[tokens[currPos].val][1]} args. Found : ${count} args`,
        pos, tokens, input, isPrefix);
}

function throwLeftBracketParseError(pos, tokens, input, isPrefix) {
    let message = isPrefix ? `Expected Operation after \'(\'.${pos === tokens.length ? '' : ` Found : ${tokens[pos].val}`}` :
                             `Expected Operation before \')\'.${pos === tokens.length ? '' : ` Found : ${tokens[pos].val}`}`;
    throw new BracketParseError(message, pos, tokens, input, isPrefix);
}

function throwIncorrectTokenParseError(pos, tokens, input, isPrefix) {
    throw new TokenParseError(`Incorrect token : ${tokens[pos].val}`, pos, tokens, input, isPrefix);
}

function throwTooManySymbolsParseError(pos, tokens, input, isPrefix) {
    throw new TokenParseError(`Excessive info`, pos, tokens, input, isPrefix);
}
