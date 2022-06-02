package com.wrenj.vm.WrenValue;

import com.wrenj.vm.WrenVM.Obj;

import java.util.Optional;

// This feels like a clumsy way to translate a union from C to Java, but it seems to be the best way.
public class ValueUnion {
    public Optional<Obj> obj;
    public Optional<Double> num;
}
