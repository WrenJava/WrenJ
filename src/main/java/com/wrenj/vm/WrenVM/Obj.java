package com.wrenj.vm.WrenVM;

import java.util.Optional;

// Base struct for all heap-allocated objects.
public class Obj {
    ObjType type;
    boolean isDark;

    // The object's class.
    ObjClass classObj;

    // The next object in the linked list of all currently allocated objects.
    Optional<Obj> next;

    public Obj(WrenVM vm, Optional<Obj> next, ObjType type, ObjClass classObj) {
        vm.first = Optional.of(this);
        this.classObj = classObj;
        this.type = type;
        this.next = next;
    }
}
