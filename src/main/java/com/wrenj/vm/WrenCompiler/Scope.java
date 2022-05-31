package com.wrenj.vm.WrenCompiler;

// Describes where a variable is declared.
public enum Scope {
    // A local variable in the current function.
    SCOPE_LOCAL,

    // A local variable declared in an enclosing function.
    SCOPE_UPVALUE,

    // A top-level module variable.
    SCOPE_MODULE
}
