package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenCompiler.ObjFn;
import com.wrenj.vm.WrenVM.Obj;

@Deprecated
public class ObjClosure {
    Obj obj;
    public ObjFn fn;
    //TODO:: // The upvalues this function has closed over.
    //  ObjUpvalue* upvalues[FLEXIBLE_ARRAY];
}
