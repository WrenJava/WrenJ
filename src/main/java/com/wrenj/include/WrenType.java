package com.wrenj.include;

public enum WrenType {
    WREN_TYPE_BOOL,
    WREN_TYPE_NUM,
    WREN_TYPE_FOREIGN,
    WREN_TYPE_LIST,
    WREN_TYPE_MAP,
    WREN_TYPE_NULL,
    WREN_TYPE_STRING,

    // The object is of a type that isn't accessible by the C API.
    WREN_TYPE_UNKNOWN
}

