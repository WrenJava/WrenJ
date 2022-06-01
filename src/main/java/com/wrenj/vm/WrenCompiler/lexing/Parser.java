package com.wrenj.vm.WrenCompiler.lexing;

import com.wrenj.vm.WrenCompiler.Token;
import com.wrenj.vm.WrenCompiler.TokenType;
import com.wrenj.vm.WrenVM.WrenVM;
import com.wrenj.vm.WrenValue.ObjModule;

import static com.wrenj.vm.WrenCompiler.TokenType.*;
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

    @Deprecated
    private void lexError(String s) {
        //TODO::implement
        System.exit(-1);
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

    // If the current character is [c], then consumes it and makes a token of type
    // [two]. Otherwise makes a token of type [one].
    void twoCharToken(char c, TokenType two, TokenType one) {
        makeToken(matchChar(c) ? two : one);
    }

    // Skips the rest of the current line.
    void skipLineComment() {
        while(peekChar() != '\n' && peekChar() != '\0') {
            nextChar();
        }
    }

    // Skips the rest of a block comment.
    public void skipBlockComment() {
        int nesting = 1;
        while (nesting > 0) {
            if (peekChar() == '\0') {
                lexError("Unterminated block comment.");
                return;
            }

            if (peekChar() == '/' && peekNextChar() == '*') {
                nextChar();
                nextChar();
                nesting++;
                continue;
            }

            if (peekChar() == '*' && peekNextChar() == '/') {
                nextChar();
                nextChar();
                nesting--;
                continue;
            }

            // Regular comment character.
            nextChar();
        }
    }

    int readHexDigit() {
        char c = nextChar();
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;

        // Don't consume it if it isn't expected. Keeps us from reading past the end
        // of an unterminated string.
        this.currentChar--;
        return -1;
    }

    @Deprecated
    void makeNumber(boolean isHex) {
        //TODO::implement
        System.exit(-1);
    }

    // Finishes lexing a hexadecimal number literal.
    void readHexNumber() {
        // Skip past the `x` used to denote a hexadecimal literal.
        nextChar();

        // Iterate over all the valid hexadecimal digits found.
        while (readHexDigit() != -1) {
            continue;
        };

        makeNumber(true);
    }

    void readNumber() {
        while (isDigit(peekChar())) nextChar();

        // See if it has a floating point. Make sure there is a digit after the "."
        // so we don't get confused by method calls on number literals.
        if (peekChar() == '.' && isDigit(peekNextChar()))
        {
            nextChar();
            while (isDigit(peekChar())) nextChar();
        }

        // See if the number is in scientific notation.
        if (matchChar('e') || matchChar('E'))
        {
            // Allow a single positive/negative exponent symbol.
            if(!matchChar('+'))
            {
                matchChar('-');
            }

            if (!isDigit(peekChar()))
            {
                lexError("Unterminated scientific notation.");
            }

            while (isDigit(peekChar())) nextChar();
        }

        makeNumber(false);
    }

    // Finishes lexing an identifier. Handles reserved words.
    @Deprecated
    void readName(TokenType type, char firstChar) {
        //TODO::implement
        System.exit(-1);
    }

    // Reads [digits] hex digits in a string literal and returns their number value.
    int readHexEscape(int digits, String description) {
        int value = 0;
        for (int i = 0; i < digits; i++) {
            if (peekChar() == '"' || peekChar() == '\0') {
                lexError("Incomplete " + description + " escape sequence.");
                this.currentChar--;
                break;
            }

            int digit = readHexDigit();
            if (digit == -1) {
                lexError("Invalid " + description + " escape sequence.");
            }

            value = (value * 16) | digit;
        }
        return value;
    }

    // Reads a hex digit Unicode escape sequence in a string literal.
    //TODO:: implment this, rn unknown what ByteBuffer is.
    // public void readUnicodeEscape(ByteBuffer* string, int length);
    @Deprecated
    public void readUnicodeEscape() {
        //TODO::implement
        System.exit(-1);
    }

    void readRawString() {
        //TODO::implement
        System.exit(-1);
    }

    void readString() {
        //TODO::implement
        System.exit(-1);
    }

    void nextToken() {
        this.previous = this.current;
        this.current = this.next;

        if (this.next.type == TokenType.TOKEN_EOF) return;
        if (this.current.type == TokenType.TOKEN_EOF) return;

        while(peekChar() != '\0') {
            this.tokenStart = this.currentChar;

            char c = nextChar();
            switch (c)
            {
                case '(':
                    // If we are inside an interpolated expression, count the unmatched "(".
                    if (this.numParens > 0) this.parens[this.numParens - 1]++;
                    makeToken(TOKEN_LEFT_PAREN);
                    return;

                case ')':
                    // If we are inside an interpolated expression, count the ")".
                    if (this.numParens > 0 &&
                            --this.parens[this.numParens - 1] == 0)
                    {
                        // This is the final ")", so the interpolation expression has ended.
                        // This ")" now begins the next section of the template string.
                        this.numParens--;
                        readString();
                        return;
                    }

                    makeToken(TOKEN_RIGHT_PAREN);
                    return;

                case '[': makeToken(TOKEN_LEFT_BRACKET); return;
                case ']': makeToken(TOKEN_RIGHT_BRACKET); return;
                case '{': makeToken(TOKEN_LEFT_BRACE); return;
                case '}': makeToken(TOKEN_RIGHT_BRACE); return;
                case ':': makeToken(TOKEN_COLON); return;
                case ',': makeToken(TOKEN_COMMA); return;
                case '*': makeToken(TOKEN_STAR); return;
                case '%': makeToken(TOKEN_PERCENT); return;
                case '#': {
                    // Ignore shebang on the first line.
                    if (this.currentLine == 1 && peekChar() == '!' && peekNextChar() == '/')
                    {
                        skipLineComment();
                        break;
                    }
                    // Otherwise we treat it as a token
                    makeToken(TOKEN_HASH);
                    return;
                }
                case '^': makeToken(TOKEN_CARET); return;
                case '+': makeToken(TOKEN_PLUS); return;
                case '-': makeToken(TOKEN_MINUS); return;
                case '~': makeToken(TOKEN_TILDE); return;
                case '?': makeToken(TOKEN_QUESTION); return;

                case '|': twoCharToken('|', TOKEN_PIPEPIPE, TOKEN_PIPE); return;
                case '&': twoCharToken('&', TOKEN_AMPAMP, TOKEN_AMP); return;
                case '=': twoCharToken('=', TOKEN_EQEQ, TOKEN_EQ); return;
                case '!': twoCharToken('=', TOKEN_BANGEQ, TOKEN_BANG); return;

                case '.':
                    if (matchChar('.'))
                    {
                        twoCharToken('.', TOKEN_DOTDOTDOT, TOKEN_DOTDOT);
                        return;
                    }

                    makeToken(TOKEN_DOT);
                    return;

                case '/':
                    if (matchChar('/'))
                    {
                        skipLineComment();
                        break;
                    }

                    if (matchChar('*'))
                    {
                        skipBlockComment();
                        break;
                    }

                    makeToken(TOKEN_SLASH);
                    return;

                case '<':
                    if (matchChar('<'))
                    {
                        makeToken(TOKEN_LTLT);
                    }
                    else
                    {
                        twoCharToken('=', TOKEN_LTEQ, TOKEN_LT);
                    }
                    return;

                case '>':
                    if (matchChar('>'))
                    {
                        makeToken(TOKEN_GTGT);
                    }
                    else
                    {
                        twoCharToken('=', TOKEN_GTEQ, TOKEN_GT);
                    }
                    return;

                case '\n':
                    makeToken(TOKEN_LINE);
                    return;

                case ' ':
                case '\r':
                case '\t':
                    // Skip forward until we run out of whitespace.
                    while (peekChar() == ' ' ||
                            peekChar() == '\r' ||
                            peekChar() == '\t')
                    {
                        nextChar();
                    }
                    break;

                case '"': {
                    if(peekChar() == '"' && peekNextChar()  == '"') {
                        readRawString();
                        return;
                    }
                    readString(); return;
                }
                case '_':
                    readName(peekChar() == '_' ? TOKEN_STATIC_FIELD : TOKEN_FIELD, c);
                    return;

                case '0':
                    if (peekChar() == 'x')
                    {
                        readHexNumber();
                        return;
                    }

                    readNumber();
                    return;

                default:
                    if (isName(c))
                    {
                        readName(TOKEN_NAME, c);
                    }
                    else if (isDigit(c))
                    {
                        readNumber();
                    }
                    else
                    {
                        if (c >= 32 && c <= 126)
                        {
                            lexError("Invalid character '" + c + "'");
                        }
                        else
                        {
                            // Don't show non-ASCII values since we didn't UTF-8 decode the
                            // bytes. Since there are no non-ASCII byte values that are
                            // meaningful code units in Wren, the lexer works on raw bytes,
                            // even though the source code and console output are UTF-8.
                            lexError("Invalid byte " + c);
                        }
                        this.next.type = TOKEN_ERROR;
                        this.next.length = 0;
                    }
                    return;
            }
        }

    }

}
