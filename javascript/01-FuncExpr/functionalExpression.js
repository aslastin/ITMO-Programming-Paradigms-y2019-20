"use strict";

const operation = op => (...f) => (...args) => op(...f.map(el => el(...args)));

const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) =>  a * b);
const divide = operation((a, b) => a / b);

const negate = operation(a => -a);
const sin = operation(Math.sin);
const cos = operation(Math.cos);
const cube = operation(a => Math.pow(a, 3));
const cuberoot = operation(Math.cbrt);
const abs = operation(Math.abs);

const iff = operation((a, b, c) => a >= 0 ? b : c);
const avg5 = operation((...param)  => param.reduce((sum, el) => sum + el) / param.length);
const med3 = operation((...param)  => param.sort((a, b) => a - b)[Math.floor(param.length / 2)]);

const getIndexOfVariable = {
    "x" : 0,
    "y" : 1,
    "z" : 2
};
const variable = a => (...variables) => variables[getIndexOfVariable[a] || 0];

const cnst = a => () => a;
const pi = () => Math.PI;
const e = () => Math.E;
const one = cnst(1);
const two = cnst(2);

const OpExpr = {
    "+" : [add, 2],
    "-" : [subtract, 2],
    "*" : [multiply, 2],
    "/" : [divide, 2],
    "negate" : [negate, 1],
    "sin" : [sin, 1],
    "cos" : [cos, 1],
    "cube" : [cube, 1],
    "cuberoot" : [cuberoot, 1],
    "abs" : [abs, 1],
    "iff" : [iff, 3],
    "avg5" : [avg5, 5],
    "med3" : [med3, 3]
};

const CnstVarExpr = {
    "x" : variable("x"),
    "y" : variable("y"),
    "z" : variable("z"),
    "pi" : pi,
    "e" : e,
    "one" : one,
    "two" : two
};

function parse(input) {
    return input.split(" ").filter(s => s.length > 0).reduce((storage, lexeme) => {
        if (OpExpr.hasOwnProperty(lexeme)) {
            storage.push(OpExpr[lexeme][0](...storage.splice(storage.length - OpExpr[lexeme][1], OpExpr[lexeme][1])));
        } else if (CnstVarExpr.hasOwnProperty(lexeme)) {
            storage.push(CnstVarExpr[lexeme]);
        } else {
            storage.push(cnst(+lexeme));
        }
       return storage;
    }, []).pop();
}
