package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenCompiler.Compiler;

import static com.wrenj.vm.WrenCompiler.TokenType.TOKEN_RIGHT_PAREN;

@Deprecated
public class GrammarFn {
    public GrammarFn() {}

    public void grouping(Compiler compiler, boolean canAssign) {
        compiler.expression();
        compiler.consume(TOKEN_RIGHT_PAREN, "Expect ')' after expression.");
    }
}
