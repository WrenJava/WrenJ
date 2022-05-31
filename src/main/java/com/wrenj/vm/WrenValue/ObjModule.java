package com.wrenj.vm.WrenValue;

import com.wrenj.vm.WrenVM.Obj;

import java.util.ArrayList;

public class ObjModule {
    Obj obj;

    // The currently defined top-level variables.
    //TODO might be wrong
    //Original was: ValueBuffer variables;
    ArrayList<Integer> variables;

    // Symbol table for the names of all module variables. Indexes here directly
    // correspond to entries in [variables].
    //TODO might be wrong
    //Original was: SymbolTable variableNames;
    ArrayList<String> variableNames;

    // The name of the module.
    String name;
}
