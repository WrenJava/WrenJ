package com.wrenj.vm.WrenCompiler;

import com.wrenj.vm.WrenVM.Obj;

import java.util.ArrayList;

public class ClassInfo {
    // The name of the class.
    String name;

    // Attributes for the class itself
    ArrayList<Obj> classAttributes;
    // Attributes for methods in this class
    ArrayList<Obj> methodAttributes;

    // Symbol table for the fields of the class.
    ArrayList<String> fields;

    // Symbols for the methods defined by the class. Used to detect duplicate
    // method definitions.
    ArrayList<Integer> methods;
    ArrayList<Integer> staticMethods;

    // True if the class being compiled is a foreign class.
    boolean isForeign;

    // True if the current method being compiled is static.
    boolean inStatic;

    // The signature of the method being compiled.
    Signature signature;
}
