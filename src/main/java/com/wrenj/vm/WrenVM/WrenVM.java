package com.wrenj.vm.WrenVM;

import com.wrenj.vm.WrenCompiler.Compiler;

import java.util.Optional;

public class WrenVM {
    public Compiler compiler;
    //The first object in the linked list of all currently allocated objects
    Optional<Obj> first;
}
