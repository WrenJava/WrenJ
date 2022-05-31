package com.wrenj.vm.WrenCompiler.lexing;

import com.wrenj.vm.WrenCompiler.TokenType;

import static com.wrenj.vm.WrenCompiler.TokenType.*;

public class Keyword {
    String identifier;
    long length;
    TokenType tokenType;
    public Keyword(String identifier, long length, TokenType tokenType) {
        this.identifier = identifier;
        this.length = length;
        this.tokenType = tokenType;
    }
    public static final Keyword[] keywords = {
            new Keyword("break",     5, TOKEN_BREAK),
            new Keyword("continue",  8, TOKEN_CONTINUE),
            new Keyword("class",     5, TOKEN_CLASS),
            new Keyword("construct", 9, TOKEN_CONSTRUCT),
            new Keyword("else",      4, TOKEN_ELSE),
            new Keyword("false",     5, TOKEN_FALSE),
            new Keyword("for",       3, TOKEN_FOR),
            new Keyword("foreign",   7, TOKEN_FOREIGN),
            new Keyword("if",        2, TOKEN_IF),
            new Keyword("import",    6, TOKEN_IMPORT),
            new Keyword("as",        2, TOKEN_AS),
            new Keyword("in",        2, TOKEN_IN),
            new Keyword("is",        2, TOKEN_IS),
            new Keyword("null",      4, TOKEN_NULL),
            new Keyword("return",    6, TOKEN_RETURN),
            new Keyword("static",    6, TOKEN_STATIC),
            new Keyword("super",     5, TOKEN_SUPER),
            new Keyword("this",      4, TOKEN_THIS),
            new Keyword("true",      4, TOKEN_TRUE),
            new Keyword("var",       3, TOKEN_VAR),
            new Keyword("while",     5, TOKEN_WHILE),
            new Keyword(null,        0, TOKEN_EOF) // Sentinel to mark the end of the array.
    };
}
