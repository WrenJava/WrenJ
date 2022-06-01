package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenCompiler.TokenType;
import com.wrenj.vm.WrenCompiler.grammar.Precedence;

import java.util.function.BiConsumer;

public class GrammarRule {
    GrammarFn prefix;
    GrammarFn infix;
    SignatureFn method;
    Precedence precedence;
    String name;
    static {
        System.exit(-1);
    }
    public GrammarRule() {
        System.exit(-1);
    }

}
