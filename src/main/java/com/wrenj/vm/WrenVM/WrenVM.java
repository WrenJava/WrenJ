package com.wrenj.vm.WrenVM;

import com.wrenj.include.WrenConfiguration;
import com.wrenj.include.unimplemented.WrenReallocateFn;
import com.wrenj.vm.WrenCompiler.Compiler;
import com.wrenj.vm.WrenValue.ObjModule;
import com.wrenj.vm.WrenValue.Value;
import com.wrenj.vm.unimplemented.*;

import java.util.ArrayList;
import java.util.Optional;

public class WrenVM {
    //HEADER FILE

    static final int WREN_MAX_TEMP_ROOTS = 8;

    //C FILE
    ObjClass boolClass;
    ObjClass classClass;
    ObjClass fiberClass;
    ObjClass fnClass;
    ObjClass listClass;
    ObjClass mapClass;
    ObjClass nullClass;
    ObjClass numClass;
    ObjClass objectClass;
    ObjClass rangeClass;
    ObjClass stringClass;

    // The fiber that is currently running.
    ObjFiber fiber;

    // The loaded modules. Each key is an ObjString (except for the main module,
    // whose key is null) for the module's name and the value is the ObjModule
    // for the module.
    ObjMap modules;

    // The most recently imported module. More specifically, the module whose
    // code has most recently finished executing.
    //
    // Not treated like a GC root since the module is already in [modules].
    ObjModule lastModule;

    // Memory management data:

    // The number of bytes that are known to be currently allocated. Includes all
    // memory that was proven live after the last GC, as well as any new bytes
    // that were allocated since then. Does *not* include bytes for objects that
    // were freed since the last GC.
    long bytesAllocated;

    // The number of total allocated bytes that will trigger the next GC.
    long nextGC;

    // The first object in the linked list of all currently allocated objects.
    Optional<Obj> first;

    // The "gray" set for the garbage collector. This is the stack of unprocessed
    // objects while a garbage collection pass is in process.
    ArrayList<Obj> gray;
    int grayCount;
    int grayCapacity;

    // The list of temporary roots. This is for temporary or new objects that are
    // not otherwise reachable but should not be collected.
    //
    // They are organized as a stack of pointers stored in this array. This
    // implies that temporary roots need to have stack semantics: only the most
    // recently pushed object can be released.
    Obj[] tempRoots = new Obj[WREN_MAX_TEMP_ROOTS];

    int numTempRoots;

    // Pointer to the first node in the linked list of active handles or NULL if
    // there are none.
    WrenHandle handles;

    // Pointer to the bottom of the range of stack slots available for use from
    // the C API. During a foreign method, this will be in the stack of the fiber
    // that is executing a method.
    //
    // If not in a foreign method, this is initially NULL. If the user requests
    // slots by calling wrenEnsureSlots(), a stack is created and this is
    // initialized.
    Value apiStack;

    WrenConfiguration config;

    // Compiler and debugger data:

    // The compiler that is currently compiling code. This is used so that heap
    // allocated objects used by the compiler can be found if a GC is kicked off
    // in the middle of a compile.
    public Compiler compiler;

    // There is a single global symbol table for all method names on all classes.
    // Method calls are dispatched directly by index in this table.
    SymbolTable methodNames;

    public WrenVM(Optional<WrenConfiguration> optionalConfig) {
        WrenReallocateFn reallocateFn;//TODO = defaultReallocate;
        Object userData = null;

        this.config = optionalConfig.orElseGet(WrenConfiguration::initConfiguration);
        this.grayCount = 0;
        this.grayCapacity = 4;
        this.gray = new ArrayList<>();
        this.nextGC = this.config.initialHeapSize;

        //TODO::wrenSymbolTableInit(&vm->methodNames);
        this.modules = null; //TODO::wrenNewMap(vm);
        initializeCore();
    }

    @Deprecated
    void collectGarbage() {
        //TODO::implement this
        System.exit(-1);
    }

    //TODO::fix the parameters
    @Deprecated
    private void wrenGrayObj(Object object) {
        //TODO::implement this
        System.exit(-1);
    }

    @Deprecated
    private void wrenGrayValue(Value value) {
        //TODO::implement this
        System.exit(-1);
    }

    private void wrenGrayBuffer(ValueBuffer buffer) {
        //TODO::implement this
        System.exit(-1);
    }

    @Deprecated
    void blackenClass(ObjClass classObj) {
        // The metaclass.
        wrenGrayObj(classObj.obj.classObj);

        // The superclass.
        wrenGrayObj(classObj.superclass);

        // Method function objects
        for (int i = 0; i < classObj.methods.count; i++) {
            //TODO::implement this
            System.exit(-1);
            /*
            if (classObj.methods.data[i].type == METHOD_BLOCK) {
                wrenGrayObj(classObj.methods.data[i].as.closure);
            }
             */
        }

        wrenGrayObj(classObj.name);

        if(classObj.attributes == null) {
            wrenGrayObj(classObj.attributes);
        }


        // Keep track of how much memory is still in use.
        //TODO::i don't think we need this, but i'ma leave it here as a todo
        /*
        vm->bytesAllocated += sizeof(ObjClass);
        vm->bytesAllocated += classObj->methods.capacity * sizeof(Method);
         */

    }

    @Deprecated
    void blackenClosure(ObjClosure closure) {
        //TODO::implement this
        System.exit(-1);
    }

    @Deprecated
    private void initializeCore() {
        //TODO::implement this
        System.exit(-1);
    }
}
