package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenCompiler.TokenType;
import com.wrenj.vm.WrenCompiler.grammar.Precedence;

@Deprecated
public class GrammarRule {
    public GrammarFn prefix;
    public GrammarFn infix;
    public SignatureFn method;
    public Precedence precedence;
    public String name;


    static {
        System.exit(-1);
    }

    public GrammarRule(GrammarFn prefix, GrammarFn infix, SignatureFn method, Precedence precedence, String name) {
        this.prefix = prefix;
        this.infix = infix;
        this.method = method;
        this.precedence = precedence;
        this.name = name;
    }

    public GrammarRule getRule(TokenType type) {
        return this;
    }
}
