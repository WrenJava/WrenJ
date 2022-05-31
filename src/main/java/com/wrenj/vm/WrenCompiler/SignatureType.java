package com.wrenj.vm.WrenCompiler;

// The different signature syntaxes for different kinds of methods.
public enum SignatureType {
    // A name followed by a (possibly empty) parenthesized parameter list. Also
    // used for binary operators.
    SIG_METHOD,

    // Just a name. Also used for unary operators.
    SIG_GETTER,

    // A name followed by "=".
    SIG_SETTER,

    // A square bracketed parameter list.
    SIG_SUBSCRIPT,

    // A square bracketed parameter list followed by "=".
    SIG_SUBSCRIPT_SETTER,

    // A constructor initializer function. This has a distinct signature to
    // prevent it from being invoked directly outside of the constructor on the
    // metaclass.
    SIG_INITIALIZER
}
