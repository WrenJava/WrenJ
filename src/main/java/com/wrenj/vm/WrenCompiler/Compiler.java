package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenVM.Obj;

import java.util.ArrayList;

import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_LOCALS;
import static com.wrenj.vm.WrenCompiler.WrenCompiler.MAX_UPVALUES;

public class Compiler {
    Parser parser;

    // The compiler for the function enclosing this one, or NULL if it's the
    // top level.
    Compiler parent;

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
    Loop loop;

    // If this is a compiler for a method, keeps track of the class enclosing it.
    ClassInfo enclosingClass;

    // The function being compiled.
    ObjFn fn;

    // The constants for the function being compiled.
    ArrayList<Obj> constants = new ArrayList<>();

    // Whether or not the compiler is for a constructor initializer
    boolean isInitializer;

    // The number of attributes seen while parsing.
    // We track this separately as compile time attributes
    // are not stored, so we can't rely on attributes->count
    // to enforce an error message when attributes are used
    // anywhere other than methods or classes.
    int numAttributes;
    // Attributes for the next class or method.
    ArrayList<Obj> attributes = new ArrayList<>();
}
