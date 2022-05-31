package com.wrenj.vm.WrenCompiler;

// A reference to a variable and the scope where it is defined. This contains
// enough information to emit correct code to load or store the variable.
public class Variable {
    // The stack slot, upvalue slot, or module symbol defining the variable.
    int index;

    // Where the variable is declared.
    Scope scope;
}
