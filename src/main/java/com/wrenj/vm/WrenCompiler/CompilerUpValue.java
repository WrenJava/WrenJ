package com.wrenj.vm.WrenCompiler;

public class CompilerUpValue {
    // True if this upvalue is capturing a local variable from the enclosing
    // function. False if it's capturing an upvalue.
    boolean isLocal;

    // The index of the local or upvalue being captured in the enclosing function.
    int index;
}
