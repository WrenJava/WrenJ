package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenCompiler.TokenType;
import com.wrenj.vm.WrenCompiler.grammar.Precedence;

// A record would be better for this purpose, but we need to
// stay with Java 8 for Minecraft reasons.
@Deprecated
public class GrammarRules {
    public GrammarRule[] grammarRules;

    // TODO::implement
    public GrammarRules() {
        System.exit(-1);
        this.grammarRules = new GrammarRule[]{
                /* TOKEN_LEFT_PAREN    */ new GrammarRule(grouping, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_RIGHT_PAREN   */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_LEFT_BRACKET  */ new GrammarRule(list, subscript, subscriptSignature, Precedence.PREC_CALL, null),
                /* TOKEN_RIGHT_BRACKET */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_LEFT_BRACE    */ new GrammarRule(map, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_RIGHT_BRACE   */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_COLON         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_DOT           */ new GrammarRule(null, call, null, Precedence.PREC_CALL, null),
                /* TOKEN_DOTDOT        */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_RANGE, ".."),
                /* TOKEN_DOTDOTDOT     */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_RANGE, "..."),
                /* TOKEN_COMMA         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_STAR          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_FACTOR, "*"),
                /* TOKEN_SLASH         */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_FACTOR, "/"),
                /* TOKEN_PERCENT       */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_FACTOR, "%"),
                /* TOKEN_HASH          */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_PLUS          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_TERM, "+"),
                /* TOKEN_MINUS         */ new GrammarRule(unaryOp, infixOp, mixedSignature, Precedence.PREC_TERM, "-"),
                /* TOKEN_LTLT          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_BITWISE_SHIFT, "<<"),
                /* TOKEN_GTGT          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_BITWISE_SHIFT, ">>"),
                /* TOKEN_PIPE          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_BITWISE_OR, "|"),
                /* TOKEN_PIPEPIPE      */ new GrammarRule(null, or_, null, Precedence.PREC_LOGICAL_OR, null),
                /* TOKEN_CARET         */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_BITWISE_XOR, "^"),
                /* TOKEN_AMP           */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_BITWISE_AND, "&"),
                /* TOKEN_AMPAMP        */ new GrammarRule(null, and_, null, Precedence.PREC_LOGICAL_AND, null),
                /* TOKEN_BANG          */ new GrammarRule(unaryOp, null, unarySignature, Precedence.PREC_NONE, "!"),
                /* TOKEN_TILDE         */ new GrammarRule(unaryOp, null, unarySignature, Precedence.PREC_NONE, "~"),
                /* TOKEN_QUESTION      */ new GrammarRule(null, conditional, null, Precedence.PREC_ASSIGNMENT, null),
                /* TOKEN_EQ            */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_LT            */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_COMPARISON, "<"),
                /* TOKEN_GT            */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_COMPARISON, ">"),
                /* TOKEN_LTEQ          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_COMPARISON, "<="),
                /* TOKEN_GTEQ          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_COMPARISON, ">="),
                /* TOKEN_EQEQ          */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_EQUALITY, "=="),
                /* TOKEN_BANGEQ        */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_EQUALITY, "!="),
                /* TOKEN_BREAK         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_CONTINUE      */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_CLASS         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_CONSTRUCT     */ new GrammarRule(null, null, constructorSignature, Precedence.PREC_NONE, null),
                /* TOKEN_ELSE          */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_FALSE         */ new GrammarRule(null /*boolean*/, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_FOR           */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_FOREIGN       */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_IF            */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_IMPORT        */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_AS            */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_IN            */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_IS            */ new GrammarRule(null, infixOp, infixSignature, Precedence.PREC_IS, "is"),
                /* TOKEN_NULL          */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_RETURN        */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_STATIC        */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_SUPER         */ new GrammarRule(super_, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_THIS          */ new GrammarRule(this_, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_TRUE          */ new GrammarRule(null /*boolean*/, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_VAR           */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_WHILE         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_FIELD         */ new GrammarRule(field, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_STATIC_FIELD  */ new GrammarRule(staticField, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_NAME          */ new GrammarRule(name, null, namedSignature, Precedence.PREC_NONE, null),
                /* TOKEN_NUMBER        */ new GrammarRule(literal, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_STRING        */ new GrammarRule(literal, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_INTERPOLATION */ new GrammarRule(stringInterpolation, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_LINE          */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_ERROR         */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null),
                /* TOKEN_EOF           */ new GrammarRule(null, null, null, Precedence.PREC_NONE, null)};
    }

    public GrammarRule getRule(TokenType type) {
        return this.grammarRules[type.ordinal()];
    }
}
