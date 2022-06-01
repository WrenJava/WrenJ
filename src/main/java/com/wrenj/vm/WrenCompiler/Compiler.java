package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenCompiler.lexing.Parser;
import com.wrenj.vm.WrenVM.Obj;
import com.wrenj.vm.WrenVM.WrenVM;
import com.wrenj.vm.WrenValue.Value;
import com.wrenj.vm.unimplemented.Code;

import java.util.ArrayList;
import java.util.Optional;

import static com.wrenj.vm.WrenCommon.MAX_VARIABLE_NAME;
import static com.wrenj.vm.WrenCompiler.Scope.*;
import static com.wrenj.vm.WrenCompiler.TokenType.*;
import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_LOCALS;
import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_UPVALUES;

public class Compiler {
    Parser parser;

    // The compiler for the function enclosing this one, or NULL if it's the
    // top level.
    Optional<Compiler> parent = Optional.empty();

    // The currently in scope local variables.
    Local[] locals = new Local[MAX_LOCALS];

    // The number of local variables currently in scope.
    int numLocals;

    // The upvalues that this function has captured from outer scopes. The count
    // of them is stored in [numUpvalues].
    CompilerUpValue[] upvalues = new CompilerUpValue[MAX_UPVALUES];

    // The current level of block scope nesting, where zero is no nesting. A -1
    // here means top-level code is being compiled and there is no block scope
    // in effect at all. Any variables declared will be module-level.
    int scopeDepth;

    // The current number of slots (locals and temporaries) in use.
    //
    // We use this and maxSlots to track the maximum number of additional slots
    // a function may need while executing. When the function is called, the
    // fiber will check to ensure its stack has enough room to cover that worst
    // case and grow the stack if needed.
    //
    // This value here doesn't include parameters to the function. Since those
    // are already pushed onto the stack by the caller and tracked there, we
    // don't need to double count them here.
    int numSlots;

    // The current innermost loop being compiled, or NULL if not in a loop.
    Optional<Loop> loop = Optional.empty();

    // If this is a compiler for a method, keeps track of the class enclosing it.
    Optional<ClassInfo> enclosingClass = Optional.empty();

    // The function being compiled.
    ObjFn fn = null;

    // The constants for the function being compiled.
    Optional<ArrayList<Obj>> constants = Optional.empty();

    // Whether or not the compiler is for a constructor initializer
    boolean isInitializer;

    // The number of attributes seen while parsing.
    // We track this separately as compile time attributes
    // are not stored, so we can't rely on attributes->count
    // to enforce an error message when attributes are used
    // anywhere other than methods or classes.
    int numAttributes;
    // Attributes for the next class or method.
    Optional<ArrayList<Obj>> attributes = Optional.empty();
    public Compiler(Parser parser, Optional<Compiler> parent, boolean isMethod) {
        this.parser = parser;
        this.parent = parent;
        this.isInitializer = false;
        this.parser.vm.compiler = this;
        // Declare a local slot for either the closure or method receiver so that we
        // don't try to reuse that slot for a user-defined local variable. For
        // methods, we name it "this", so that we can resolve references to that like
        // a normal variable. For functions, they have no explicit "this", so we use
        // an empty name. That way references to "this" inside a function walks up
        // the parent chain to find a method enclosing the function whose "this" we
        // can close over.
        this.numLocals = 1;
        this.numSlots = this.numLocals;

        if (isMethod) {
            this.locals[0].name = "this";
            this.locals[0].length = 4;
        } else {
            this.locals[0].name = null;
            this.locals[0].length = 0;
        }

        this.locals[0].depth = -1;
        this.locals[0].isUpvalue = false;

        if (parent.isPresent()) {
            // The initial scope for functions and methods is local scope.
            this.scopeDepth = 0;
        } else {
            // Compiling top-level code, so the initial scope is module-level.
            this.scopeDepth = -1;
        }

        this.numAttributes = 0;
        this.attributes = Optional.of(new ArrayList<>());
        this.fn = new ObjFn(this.parser.module, this.numLocals);
    }

    int addConstant(Value constant) {
        if(parser.hasError) return -1;
        if(!constants.isPresent()) {
            //TODO::implement
            System.exit(-1);
        }
        //TODO::implement
        System.exit(-1);
        return 0;
    }


    // Returns the type of the current token.
    TokenType peek() {
        return this.parser.current.type;
    }

    // Returns the type of the next token.
    TokenType peekNext() {
        return this.parser.next.type;
    }

    // Consumes the current token if its type is [expected]. Returns true if a
    // token was consumed.
    boolean match(TokenType expected) {
        if (peek() != expected) return false;

        parser.nextToken();
        return true;
    }

    // Consumes the current token. Emits an error if its type is not [expected].
    void consume(TokenType expected, String errorMessage) {
        parser.nextToken();
        if (this.parser.previous.type != expected) {
            error(errorMessage);

            if (parser.current.type == expected) {
                parser.nextToken();
            }
        }
    }

    // Matches one or more newlines. Returns true if at least one was found.
    boolean matchLine() {
        if (!match(TOKEN_LINE)) return false;

        while (match(TOKEN_LINE));
        return true;
    }

    // Discards any newlines starting at the current token.
    void ignoreNewlines() {
        matchLine();
    }

    // Consumes the current token. Emits an error if it is not a newline. Then
    // discards any duplicate newlines following it.
    public void consumeLine(String errorMessage) {
        consume(TOKEN_LINE, errorMessage);
        ignoreNewlines();
    }

    public void allowLineBeforeDot() {
        if (peek() == TOKEN_LINE && peekNext() == TOKEN_DOT) {
            parser.nextToken();
        }
    }

    // Emits one single-byte argument. Returns its index.
    @Deprecated
    int emitByte(int _byte) {
        //TODO::implement
        System.exit(-1);
        return 0;
    }

    @Deprecated
    void emitOp(Code instruction) {
        //TODO::implement
        System.exit(-1);
    }

    // Emits one 16-bit argument, which will be written big endian.
    void emitShort(int arg) {
        emitByte((arg >> 8) & 0xff);
        emitByte(arg & 0xff);
    }

    // Emits one bytecode instruction followed by a 8-bit argument. Returns the
    // index of the argument in the bytecode.
    int emitByteArg(Code instruction, int arg) {
        emitOp(instruction);
        return emitByte(arg);
    }

    // Emits one bytecode instruction followed by a 16-bit argument, which will be
    // written big endian.
    void emitShortArg(Code instruction, int arg) {
        emitOp(instruction);
        emitShort(arg);
    }

    // Emits [instruction] followed by a placeholder for a jump offset. The
    // placeholder can be patched by calling [jumpPatch]. Returns the index of the
    // placeholder.
    int emitJump(Code instruction) {
        emitOp(instruction);
        emitByte(0xff);
        return emitByte(0xff) - 1;
    }

    // Creates a new constant for the current value and emits the bytecode to load
    // it from the constant table.
    void emitConstant(Value value) {
        int constant = addConstant(value);

        // Compile the code to load the constant.
        //TODO::implement
        System.exit(-1);
        //CODE_CONSTANT doesn't exist yet
        //emitShortArg(CODE_CONSTANT, constant);
    }

    // Create a new local variable with [name]. Assumes the current scope is local
    // and the name is unique.
    int addLocal(String name, int length) {
        if (locals[numLocals] == null) {
            locals[numLocals] = new Local();
        }
        Local local = locals[numLocals];
        local.name = name;
        local.length = length;
        local.depth = scopeDepth;
        local.isUpvalue = false;
        numLocals++;
        return numLocals;
    }


    // Declares a variable in the current scope whose name is the given token.
    // If [token] is `NULL`, uses the previously consumed token. Returns its symbol.
    @Deprecated
    int declareVariable(Token token) {
        if (token == null) {
            token = this.parser.previous;
        }
        if (token.length > MAX_VARIABLE_NAME) {
            error("Variable name cannot be longer than " + MAX_VARIABLE_NAME + " characters.");
        }

        // Top-level module scope.
        if (scopeDepth == -1) {
            int line = -1;
            int symbol = 0; System.exit(-1);//TODO::implement wrenDefineVariable

            if (symbol == -1) {
                error("Module variable is already defined.");
            } else if (symbol == -2) {
                error("Too many module variables defined.");
            } else if (symbol == -3) {
                error("Variable " + parser.source.substring(token.start, token.start+token.length) +
                        " referenced before this definition (first use at line " + line + ").");
            }
            return symbol;
        }
        // See if there is already a variable with this name declared in the current
        // scope. (Outer scopes are OK: those get shadowed.)
        for (int i = numLocals - 1; i >= 0; i--)
        {
            Local local = locals[i];

            // Once we escape this scope and hit an outer one, we can stop.
            if (local.depth < scopeDepth) break;

            //TODO::port this line, takes brain power so moving on for now.
            System.exit(-1);
            return -1;
            /*
            if (local.length == token.length &&
                    memcmp(local->name, token->start, token->length) == 0)
            {
                error("Variable is already declared in this scope.");
                return i;
            }
             */
        }

        if(numLocals == MAX_LOCALS) {
            error("Cannot declare more than " + MAX_LOCALS + " variables in one scope.");
            return -1;
        }
        return addLocal(parser.source.substring(token.start, token.start + token.length), token.length);
    }

    // Parses a name token and declares a variable in the current scope with that
    // name. Returns its slot.
    int declareNamedVariable() {
        consume(TOKEN_NAME, "Expect variable name.");
        return declareVariable(null);
    }

    // Stores a variable with the previously defined symbol in the current scope.
    @Deprecated
    void defineVariable(int symbol) {
        // Store the variable. If it's a local, the result of the initializer is
        // in the correct slot on the stack already so we're done.
        if (scopeDepth >= 0) return;

        // It's a module-level variable, so store the value in the module slot and
        // then discard the temporary for the initializer.
        //TODO::implement this
        System.exit(-1);
        //emitShortArg(CODE_STORE_MODULE_VAR, symbol);
        //emitOp(CODE_POP);
    }

    // Starts a new local block scope.
    void pushScope() {
        scopeDepth++;
    }

    // Generates code to discard local variables at [depth] or greater. Does *not*
    // actually undeclare variables or pop any scopes, though. This is called
    // directly when compiling "break" statements to ditch the local variables
    // before jumping out of the loop even though they are still in scope *past*
    // the break instruction.
    //
    // Returns the number of local variables that were eliminated.
    @Deprecated
    static int discardLocals(int depth)
    {
        //TODO::implement this
        System.exit(-1);
        return -1;
        /*ASSERT(compiler->scopeDepth > -1, "Cannot exit top-level scope.");

        int local = compiler->numLocals - 1;
        while (local >= 0 && compiler->locals[local].depth >= depth)
        {
            // If the local was closed over, make sure the upvalue gets closed when it
            // goes out of scope on the stack. We use emitByte() and not emitOp() here
            // because we don't want to track that stack effect of these pops since the
            // variables are still in scope after the break.
            if (compiler->locals[local].isUpvalue)
            {
                emitByte(compiler, CODE_CLOSE_UPVALUE);
            }
            else
            {
                emitByte(compiler, CODE_POP);
            }


            local--;
        }

        return compiler->numLocals - local - 1;*/
    }

    // Closes the last pushed block scope and discards any local variables declared
    // in that scope. This should only be called in a statement context where no
    // temporaries are still on the stack.
    void popScope() {
        int popped = discardLocals(scopeDepth);
        numLocals -= popped;
        numSlots -= popped;
        scopeDepth--;
    }

    // Attempts to look up the name in the local variables of [compiler]. If found,
    // returns its index, otherwise returns -1.
    @Deprecated
    int resolveLocal(String name, int length) {
        // Look it up in the local scopes. Look in reverse order so that the most
        // nested variable is found first and shadows outer ones.
        //TODO::implement
        System.exit(-1);
        return -1;
        /*
        for (int i = compiler->numLocals - 1; i >= 0; i--)
        {
            if (compiler->locals[i].length == length &&
                    memcmp(name, compiler->locals[i].name, length) == 0)
            {
                return i;
            }
        }

        return -1;
         */
    }


    // Adds an upvalue to [compiler]'s function with the given properties. Does not
    // add one if an upvalue for that variable is already in the list. Returns the
    // index of the upvalue.
    int addUpvalue(boolean isLocal, int index) {
        // Look for an existing one.

        for (int i = 0; i < fn.numUpvalues; i++)
        {
            CompilerUpValue upvalue = upvalues[i];
            if (upvalue.index == index && upvalue.isLocal == isLocal) return i;
        }

        // If we got here, it's a new upvalue.
        upvalues[fn.numUpvalues].isLocal = isLocal;
        upvalues[fn.numUpvalues].index = index;
        return fn.numUpvalues++;
    }

    // Attempts to look up [name] in the functions enclosing the one being compiled
    // by [compiler]. If found, it adds an upvalue for it to this compiler's list
    // of upvalues (unless it's already in there) and returns its index. If not
    // found, returns -1.
    //
    // If the name is found outside of the immediately enclosing function, this
    // will flatten the closure and add upvalues to all of the intermediate
    // functions so that it gets walked down to this one.
    //
    // If it reaches a method boundary, this stops and returns -1 since methods do
    // not close over local variables.
    int findUpvalue(String name, int length)
    {
        // If we are at the top level, we didn't find it.
        if (!parent.isPresent()) return -1;

        // If we hit the method boundary (and the name isn't a static field), then
        // stop looking for it. We'll instead treat it as a self send.
        if (name.charAt(0) != '_' && parent.get().enclosingClass.isPresent()) return -1;

        // See if it's a local variable in the immediately enclosing function.
        int local = parent.get().resolveLocal(name, length);
        if (local != -1)
        {
            // Mark the local as an upvalue so we know to close it when it goes out of
            // scope.
            parent.get().locals[local].isUpvalue = true;

            return addUpvalue(true, local);
        }

        // See if it's an upvalue in the immediately enclosing function. In other
        // words, if it's a local variable in a non-immediately enclosing function.
        // This "flattens" closures automatically: it adds upvalues to all of the
        // intermediate functions to get from the function where a local is declared
        // all the way into the possibly deeply nested function that is closing over
        // it.
        int upvalue = parent.get().findUpvalue(name, length);
        if (upvalue != -1)
        {
            return addUpvalue(false, upvalue);
        }

        // If we got here, we walked all the way up the parent chain and couldn't
        // find it.
        return -1;
    }

    // Look up [name] in the current scope to see what variable it refers to.
    // Returns the variable either in local scope, or the enclosing function's
    // upvalue list. Does not search the module scope. Returns a variable with
    // index -1 if not found.
    Variable resolveNonmodule(String name, int length) {
        // Look it up in the local scopes.
        Variable variable = new Variable();
        variable.scope = SCOPE_LOCAL;
        variable.index = resolveLocal(name, length);
        if (variable.index != -1) return variable;

        // Tt's not a local, so guess that it's an upvalue.
        variable.scope = SCOPE_UPVALUE;
        variable.index = findUpvalue(name, length);
        return variable;
    }

    // Look up [name] in the current scope to see what variable it refers to.
    // Returns the variable either in module scope, local scope, or the enclosing
    // function's upvalue list. Returns a variable with index -1 if not found.
    @Deprecated
    Variable resolveName(String name, int length)
    {
        Variable variable = resolveNonmodule(name, length);
        if (variable.index != -1) return variable;

        variable.scope = SCOPE_MODULE;
        //TODO::implement this
        System.exit(-1);
        return null;
        /*
        variable.index = wrenSymbolTableFind(&compiler->parser->module->variableNames,
                name, length);
        return variable;
        */
    }

    @Deprecated
    void loadLocal(int slot)
    {
        if (slot <= 8)
        {
            //TODO::implement this
            System.exit(-1);
            return;
            /*
            emitOp((Code)(CODE_LOAD_LOCAL_0 + slot));
            return;
             */
        }
        //TODO::implement this
        return;
        /*
        emitByteArg(CODE_LOAD_LOCAL, slot);
         */
    }

    // Finishes [compiler], which is compiling a function, method, or chunk of top
    // level code. If there is a parent compiler, then this emits code in the
    // parent compiler to load the resulting function.
    @Deprecated
    ObjFn endCompiler(String debugName, int debugNameLength)
    {
        // If we hit an error, don't finish the function since it's borked anyway.
        if (parser.hasError)
        {
            parser.vm.compiler = parent.get();
            return null;
        }

        // Mark the end of the bytecode. Since it may contain multiple early returns,
        // we can't rely on CODE_RETURN to tell us we're at the end.
        //TODO::implement this
        System.exit(-1);
        return null;
        /*
        emitOp(compiler, CODE_END);

        wrenFunctionBindName(compiler->parser->vm, compiler->fn,
                debugName, debugNameLength);

        // In the function that contains this one, load the resulting function object.
        if (compiler->parent != NULL)
        {
            int constant = addConstant(compiler->parent, OBJ_VAL(compiler->fn));

            // Wrap the function in a closure. We do this even if it has no upvalues so
            // that the VM can uniformly assume all called objects are closures. This
            // makes creating a function a little slower, but makes invoking them
            // faster. Given that functions are invoked more often than they are
            // created, this is a win.
            emitShortArg(compiler->parent, CODE_CLOSURE, constant);

            // Emit arguments for each upvalue to know whether to capture a local or
            // an upvalue.
            for (int i = 0; i < compiler->fn->numUpvalues; i++)
            {
                emitByte(compiler->parent, compiler->upvalues[i].isLocal ? 1 : 0);
                emitByte(compiler->parent, compiler->upvalues[i].index);
            }
        }

        // Pop this compiler off the stack.
        compiler->parser->vm->compiler = compiler->parent;

          #if WREN_DEBUG_DUMP_COMPILED_CODE
                wrenDumpCode(compiler->parser->vm, compiler->fn);
          #endif

        return compiler->fn;

         */
    }

    void error(String message) {
        //TODO::implement
        System.err.println(message);
        System.exit(-1);
    }
}
