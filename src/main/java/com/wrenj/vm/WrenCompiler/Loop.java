package com.wrenj.vm.WrenCompiler;

import java.util.Optional;

public class Loop {
    // Index of the instruction that the loop should jump back to.
    int start;

    // Index of the argument for the CODE_JUMP_IF instruction used to exit the
    // loop. Stored so we can patch it once we know where the loop ends.
    int exitJump;

    // Index of the first instruction of the body of the loop.
    int body;

    // Depth of the scope(s) that need to be exited if a break is hit inside the
    // loop.
    int scopeDepth;

    // The loop enclosing this one, or NULL if this is the outermost loop.
    Loop enclosing;
}
