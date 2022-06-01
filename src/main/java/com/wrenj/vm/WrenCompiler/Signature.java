package com.wrenj.vm.WrenCompiler;

//TODO::document
public class Signature {
    // This is how `const char* name` can be translated into Java. We should probably do this in other places as well.
    String nameStr;
    long nameLoc;

    int length;
    SignatureType type;
    int arity;
}
