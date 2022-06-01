package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenValue.Value;

public class Token {
    public TokenType type;

    // The beginning of the token, pointing directly into the source.
    public int start;

    // The length of the token in characters.
    public int length;

    // The 1-based line where the token appears
    public int line;

    // The parsed value if the token is a literal.
    public Value value;

}
