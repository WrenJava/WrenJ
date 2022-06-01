package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenVM.Obj;
import com.wrenj.vm.WrenValue.ObjModule;
import com.wrenj.vm.unimplemented.ByteBuffer;

import java.util.ArrayList;

// A function object. It wraps and owns the bytecode and other debug information
// for a callable chunk of code.
//
// Function objects are not passed around and invoked directly. Instead, they
// are always referenced by an [ObjClosure] which is the real first-class
// representation of a function. This isn't strictly necessary if they function
// has no upvalues, but lets the rest of the VM assume all called objects will
// be closures.
public class ObjFn {
    Obj obj;

    ByteBuffer code;
    ArrayList<Integer> constants;

    // The module where this function was defined.
    ObjModule module;

    // The maximum number of stack slots this function may use.
    int maxSlots;

    // The number of upvalues this function closes over.
    public int numUpvalues;

    // The number of parameters this function expects. Used to ensure that .call
    // handles a mismatch between number of parameters and arguments. This will
    // only be set for fns, and not ObjFns that represent methods or scripts.
    int arity;
    FnDebug debug;
    public ObjFn(ObjModule module, int maxSlots) {
        this.module = module;
        this.maxSlots = maxSlots;
    }
}
class FnDebug {
    ArrayList<Integer> sourceLines;
    String nameStr;
    long nameLoc;
}