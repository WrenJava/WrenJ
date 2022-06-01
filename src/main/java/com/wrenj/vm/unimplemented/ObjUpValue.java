package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenVM.Obj;
import com.wrenj.vm.WrenValue.Value;

import java.util.Optional;

@Deprecated
public class ObjUpValue {
    // The object header. Note that upvalues have this because they are garbage
    // collected, but they are not first class Wren objects.
    Obj obj;

    // Pointer to the variable this upvalue is referencing.
    public Value value;

    // If the upvalue is closed (i.e. the local variable it was pointing to has
    // been popped off the stack) then the closed-over value will be hoisted out
    // of the stack into here. [value] will then be changed to point to this.
    Value closed;

    // Open upvalues are stored in a linked list by the fiber. This points to the
    // next upvalue in that list.
    public ObjUpValue next;
}
