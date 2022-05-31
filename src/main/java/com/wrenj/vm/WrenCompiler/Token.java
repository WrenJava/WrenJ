package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenValue.Value;

public class Token {
    TokenType type;

    // The beginning of the token, pointing directly into the source.
    long start;

    // The length of the token in characters.
    int length;

    // The 1-based line where the token appears
    int line;

    // The parsed value if the token is a literal.
    Value value;

}
