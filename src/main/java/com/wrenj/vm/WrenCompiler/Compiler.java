package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenCompiler.lexing.Parser;
import com.wrenj.vm.WrenVM.Obj;

import java.util.ArrayList;
import java.util.Optional;

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
    Optional<ObjFn> fn = Optional.empty();

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
        this.fn = Optional.of(new ObjFn(this.parser.module, this.numLocals));
    }
}
