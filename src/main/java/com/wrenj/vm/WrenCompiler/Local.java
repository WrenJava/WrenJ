package com.wrenj.vm.WrenCompiler;

public class Local {
    // The name of the local variable. This points directly into the original
    // source code string.
    //const char* name;
    String nameStr;
    long nameLoc;

    // The length of the local variable's name.
    int length;

    // The depth in the scope chain that this variable was declared at. Zero is
    // the outermost scope--parameters for a method, or the first local block in
    // top level code. One is the scope within that, etc.
    int depth;

    // If this local variable is being used as an upvalue.
    boolean isUpvalue;
}
