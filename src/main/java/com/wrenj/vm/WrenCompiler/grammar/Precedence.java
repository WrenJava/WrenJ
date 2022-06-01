package com.wrenj.vm.WrenCompiler.grammar;

public enum Precedence {
    PREC_NONE,
    PREC_LOWEST,
    PREC_ASSIGNMENT,    // =
    PREC_CONDITIONAL,   // ?:
    PREC_LOGICAL_OR,    // ||
    PREC_LOGICAL_AND,   // &&
    PREC_EQUALITY,      // == !=
    PREC_IS,            // is
    PREC_COMPARISON,    // < > <= >=
    PREC_BITWISE_OR,    // |
    PREC_BITWISE_XOR,   // ^
    PREC_BITWISE_AND,   // &
    PREC_BITWISE_SHIFT, // << >>
    PREC_RANGE,         // .. ...
    PREC_TERM,          // + -
    PREC_FACTOR,        // * / %
    PREC_UNARY,         // unary - ! ~
    PREC_CALL,          // . () []
    PREC_PRIMARY
}
