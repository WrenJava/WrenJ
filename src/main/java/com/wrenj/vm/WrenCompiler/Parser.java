package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenVM.WrenVM;
import com.wrenj.vm.WrenValue.ObjModule;

import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_INTERPOLATION_NESTING;

public class Parser {
    WrenVM vm;

    // The module being parsed.
    ObjModule module;

    // The source code being parsed.
    String source;

    // The beginning of the currently-being-lexed token in [source].
    long tokenStart;
    //const char* tokenStart;

    // The current character being lexed in [source].
    long currentChar;
    //const char* currentChar;

    // The 1-based line number of [currentChar].
    int currentLine;

    // The upcoming token.
    Token next;

    // The most recently lexed token.
    Token current;

    // The most recently consumed/advanced token.
    Token previous;

    // Tracks the lexing state when tokenizing interpolated strings.
    //
    // Interpolated strings make the lexer not strictly regular: we don't know
    // whether a ")" should be treated as a RIGHT_PAREN token or as ending an
    // interpolated expression unless we know whether we are inside a string
    // interpolation and how many unmatched "(" there are. This is particularly
    // complex because interpolation can nest:
    //
    //     " %( " %( inner ) " ) "
    //
    // This tracks that state. The parser maintains a stack of ints, one for each
    // level of current interpolation nesting. Each value is the number of
    // unmatched "(" that are waiting to be closed.
    int[] parens = new int[MAX_INTERPOLATION_NESTING];
    int numParens;

    // Whether compile errors should be printed to stderr or discarded.
    boolean printErrors;

    // If a syntax or compile error has occurred.
    boolean hasError;
}
