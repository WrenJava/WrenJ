package com.wrenj.vm.WrenVM;

import com.wrenj.vm.WrenValue.Value;

import java.util.ArrayList;

public class ObjClass {
    Obj obj;
    ObjClass superclass;

    // The number of fields needed for an instance of this class, including all
    // of its superclass fields.
    int numFields;

    // The table of methods that are defined in or inherited by this class.
    // Methods are called by symbol, and the symbol directly maps to an index in
    // this table. This makes method calls fast at the expense of empty cells in
    // the list for methods the class doesn't support.
    //
    // You can think of it as a hash table that never has collisions but has a
    // really low load factor. Since methods are pretty small (just a type and a
    // pointer), this should be a worthwhile trade-off.
    ArrayList<Integer> methods;

    // The name of the class.
    String name;

    // The ClassAttribute for the class, if any
    Value attributes;
}
