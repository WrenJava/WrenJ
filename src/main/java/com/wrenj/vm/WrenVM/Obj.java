package com.wrenj.vm.WrenVM;

public class Obj {
    ObjType type;
    boolean isDark;

    // The object's class.
    ObjClass classObj;

    // The next object in the linked list of all currently allocated objects.
    Obj next;
}
