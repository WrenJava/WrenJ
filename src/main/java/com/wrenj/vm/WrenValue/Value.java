package com.wrenj.vm.WrenValue;

import com.wrenj.vm.WrenVM.Obj;

import java.util.Optional;


public class Value {
    ValueType type;
    ValueUnion as;
}
class ValueUnion {
    Optional<Obj> obj;
    Optional<Double> num;
}
