package com.wrenj.vm.WrenValue;

import com.wrenj.vm.unimplemented.ObjFiber;

public class WrenValue {
    public static boolean wrenHasError(ObjFiber fiber) {
        return fiber.error == null;
    }
}
