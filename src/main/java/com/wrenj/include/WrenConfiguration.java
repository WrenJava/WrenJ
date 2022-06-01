package com.wrenj.include;

import com.wrenj.include.unimplemented.*;

public class WrenConfiguration {
    // The callback Wren will use to allocate, reallocate, and deallocate memory.
    //
    // If `NULL`, defaults to a built-in function that uses `realloc` and `free`.
    public WrenReallocateFn reallocateFn;

    // The callback Wren uses to resolve a module name.
    //
    // Some host applications may wish to support "relative" imports, where the
    // meaning of an import string depends on the module that contains it. To
    // support that without baking any policy into Wren itself, the VM gives the
    // host a chance to resolve an import string.
    //
    // Before an import is loaded, it calls this, passing in the name of the
    // module that contains the import and the import string. The host app can
    // look at both of those and produce a new "canonical" string that uniquely
    // identifies the module. This string is then used as the name of the module
    // going forward. It is what is passed to [loadModuleFn], how duplicate
    // imports of the same module are detected, and how the module is reported in
    // stack traces.
    //
    // If you leave this function NULL, then the original import string is
    // treated as the resolved string.
    //
    // If an import cannot be resolved by the embedder, it should return NULL and
    // Wren will report that as a runtime error.
    //
    // Wren will take ownership of the string you return and free it for you, so
    // it should be allocated using the same allocation function you provide
    // above.
    WrenResolveModuleFn resolveModuleFn;

    // The callback Wren uses to load a module.
    //
    // Since Wren does not talk directly to the file system, it relies on the
    // embedder to physically locate and read the source code for a module. The
    // first time an import appears, Wren will call this and pass in the name of
    // the module being imported. The method will return a result, which contains
    // the source code for that module. Memory for the source is owned by the
    // host application, and can be freed using the onComplete callback.
    //
    // This will only be called once for any given module name. Wren caches the
    // result internally so subsequent imports of the same module will use the
    // previous source and not call this.
    //
    // If a module with the given name could not be found by the embedder, it
    // should return NULL and Wren will report that as a runtime error.
    WrenLoadModuleFn loadModuleFn;

    // The callback Wren uses to find a foreign method and bind it to a class.
    //
    // When a foreign method is declared in a class, this will be called with the
    // foreign method's module, class, and signature when the class body is
    // executed. It should return a pointer to the foreign function that will be
    // bound to that method.
    //
    // If the foreign function could not be found, this should return NULL and
    // Wren will report it as runtime error.
    WrenBindForeignMethodFn bindForeignMethodFn;

    // The callback Wren uses to find a foreign class and get its foreign methods.
    //
    // When a foreign class is declared, this will be called with the class's
    // module and name when the class body is executed. It should return the
    // foreign functions uses to allocate and (optionally) finalize the bytes
    // stored in the foreign object when an instance is created.
    WrenBindForeignClassFn bindForeignClassFn;

    // The callback Wren uses to display text when `System.print()` or the other
    // related functions are called.
    //
    // If this is `NULL`, Wren discards any printed text.
    WrenWriteFn writeFn;

    // The callback Wren uses to report errors.
    //
    // When an error occurs, this will be called with the module name, line
    // number, and an error message. If this is `NULL`, Wren doesn't report any
    // errors.
    WrenErrorFn errorFn;

    // The number of bytes Wren will allocate before triggering the first garbage
    // collection.
    //
    // If zero, defaults to 10MB.
    public long initialHeapSize;

    // After a collection occurs, the threshold for the next collection is
    // determined based on the number of bytes remaining in use. This allows Wren
    // to shrink its memory usage automatically after reclaiming a large amount
    // of memory.
    //
    // This can be used to ensure that the heap does not get too small, which can
    // in turn lead to a large number of collections afterwards as the heap grows
    // back to a usable size.
    //
    // If zero, defaults to 1MB.
    long minHeapSize;

    // Wren will resize the heap automatically as the number of bytes
    // remaining in use after a collection changes. This number determines the
    // amount of additional memory Wren will use after a collection, as a
    // percentage of the current heap size.
    //
    // For example, say that this is 50. After a garbage collection, when there
    // are 400 bytes of memory still in use, the next collection will be triggered
    // after a total of 600 bytes are allocated (including the 400 already in
    // use.)
    //
    // Setting this to a smaller number wastes less memory, but triggers more
    // frequent garbage collections.
    //
    // If zero, defaults to 50.
    int heapGrowthPercent;

    // User-defined data associated with the VM.
    public Object userData;

    public static WrenConfiguration initConfiguration() {
        WrenConfiguration config = new WrenConfiguration();
        config.reallocateFn = null; //TODO = defaultReallocate;
        config.resolveModuleFn = null;
        config.loadModuleFn = null;
        config.bindForeignMethodFn = null;
        config.bindForeignClassFn = null;
        config.writeFn = null;
        config.errorFn = null;
        config.initialHeapSize = 1024 * 1024 * 10;
        config.minHeapSize = 1024 * 1024;
        config.heapGrowthPercent = 50;
        config.userData = null;
        return config;
    }
}
