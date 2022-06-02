package com.wrenj.vm.WrenValue;

import com.wrenj.vm.unimplemented.ObjClosure;

public class CallFrame {
    // The current (next-to-be-executed) instruction in the function's bytecode
    // FIXME::This shouldn't be an int, I don't think...
    int ip;

    // The closure being executed
    ObjClosure closure;

    // The first stack slot used by this call frame. This will contain the receiver,
    // followed by the function's parameters, then local variables and temporaries.
    Value stackValue;
}
