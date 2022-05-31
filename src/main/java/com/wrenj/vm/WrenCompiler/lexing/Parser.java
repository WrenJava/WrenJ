package com.wrenj.vm.WrenCompiler.lexing;

import com.wrenj.vm.WrenCompiler.Token;
import com.wrenj.vm.WrenCompiler.TokenType;
import com.wrenj.vm.WrenVM.WrenVM;
import com.wrenj.vm.WrenValue.ObjModule;

import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_INTERPOLATION_NESTING;

public class Parser {
    public WrenVM vm;

    // The module being parsed.
    public ObjModule module;

    // The source code being parsed.
    String source;

    // The beginning of the currently-being-lexed token in [source].
    int tokenStart;
    //const char* tokenStart;

    // The current character being lexed in [source].
    int currentChar;
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

    // Returns true if [c] is a valid (non-initial) identifier character.
    public static boolean isName(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    // Returns true if [c] is a digit.
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    // Returns the current character the parser is sitting on.
    public char peekChar() {
        return source.charAt(this.currentChar);
    }

    // Returns the character after the current character.
    //TODO:: I might have screwed up this here, it might be currentChar+2, I can't remember how length works compared to indexing.
    public char peekNextChar() {
        // If we're at the end of the source, don't read past it.
        if (source.length() == currentChar+1) {
            return '\0';
        }
        return source.charAt(currentChar+1);
    }

    // Advances the parser forward one character.
    public char nextChar() {
        char c = peekChar();
        currentChar++;
        if (c == '\n') this.currentLine++;
        return c;
    }

    // If the current character is [c], consumes it and returns `true`.
    public boolean matchChar(char c) {
        if (peekChar() != c) return false;
        nextChar();
        return true;
    }

    // Sets the parser's current token to the given [type] and current character range.
    void makeToken(TokenType type) {
        this.next.type = type;
        this.next.start = this.tokenStart;
        this.next.length = (this.currentChar - this.tokenStart);
        this.next.line = this.currentLine;

        // Make line tokens appear on the line containing the "\n".
        if (type == TokenType.TOKEN_LINE) this.next.line--;
    }
}
