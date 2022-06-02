package com.wrenj.vm.unimplemented;

import com.wrenj.vm.WrenVM.FiberState;
import com.wrenj.vm.WrenVM.Obj;
import com.wrenj.vm.WrenValue.CallFrame;
import com.wrenj.vm.WrenValue.Value;

import java.util.Optional;

@Deprecated
public class ObjFiber {
    static {
        System.exit(-1);
    }
    Obj obj;

    // The stack of value slots. This is used for holding local variables and
    // temporaries while the fiber is executing. It is heap-allocated and grown
    // as needed.
    Value stack;

    // A pointer to one past the top-most value on the stack.
    public Value stackTop;

    // The number of allocated slots in the stack array.
    int stackCapacity;

    // The stack of call frames. This is a dynamic array that grows as needed but
    // never shrinks.
    CallFrame frames;

    // The number of frames currently in use in [frames].
    int numFrames;

    // The number of [frames] allocated.
    int frameCapacity;

    // Pointer to the first node in the linked list of open upvalues that are
    // pointing to values still on the stack. The head of the list will be the
    // upvalue closest to the top of the stack, and then the list works downwards.
    public ObjUpValue openUpvalues;

    // The fiber that ran this one. If this fiber is yielded, control will resume
    // to this one. May be `NULL`.
    public Optional<ObjFiber> caller;

    // If the fiber failed because of a runtime error, this will contain the
    // error object. Otherwise, it will be null.
    public Value error;

    public FiberState state;
}
