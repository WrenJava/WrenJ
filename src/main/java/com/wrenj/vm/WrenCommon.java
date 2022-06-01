package com.wrenj.vm;

public class WrenCommon {
    public static final int MAX_VARIABLE_NAME = 64;
    public static final int MAX_PARAMETERS = 16;

    public static final int MAX_METHOD_NAME = 64;

    public static final int MAX_METHOD_SIGNATURE = (MAX_METHOD_NAME + (MAX_PARAMETERS * 2) + 6);
}
