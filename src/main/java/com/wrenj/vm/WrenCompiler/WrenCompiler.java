package com.wrenj.vm.WrenCompiler;

import static com.wrenj.vm.WrenCommon.MAX_VARIABLE_NAME;

public class WrenCompiler {
    // This is written in bottom-up order, so the tokenization comes first, then
    // parsing/code generation. This minimizes the number of explicit forward
    // declarations needed.

        // The maximum number of local (i.e. not module level) variables that can be
    // declared in a single function, method, or chunk of top level code. This is
    // the maximum number of variables in scope at one time, and spans block scopes.
    //
    // Note that this limitation is also explicit in the bytecode. Since
    // `CODE_LOAD_LOCAL` and `CODE_STORE_LOCAL` use a single argument byte to
    // identify the local, only 256 can be in scope at one time.
    public static final int MAX_LOCALS = 256;

    // The maximum number of upvalues (i.e. variables from enclosing functions)
    // that a function can close over.
    public static final int MAX_UPVALUES = 256;

    // The maximum number of distinct constants that a function can contain. This
    // value is explicit in the bytecode since `CODE_CONSTANT` only takes a single
    // two-byte argument.
    public static final int MAX_CONSTANTS = (1 << 16);

    // The maximum distance a CODE_JUMP or CODE_JUMP_IF instruction can move the
    // instruction pointer.
    public static final int MAX_JUMP = (1 << 16);

    // The maximum depth that interpolation can nest. For example, this string has
    // three levels:
    //
    //      "outside %(one + "%(two + "%(three)")")"
    public static final int MAX_INTERPOLATION_NESTING = 8;

    // The buffer size used to format a compile error message, excluding the header
    // with the module name and error location. Using a hardcoded buffer for this
    // is kind of hairy, but fortunately we can control what the longest possible
    // message is and handle that. Ideally, we'd use `snprintf()`, but that's not
    // available in standard C++98.
    public static final int ERROR_MESSAGE_SIZE = (80 + MAX_VARIABLE_NAME + 15);
}
