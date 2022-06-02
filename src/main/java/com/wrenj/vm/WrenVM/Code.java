package com.wrenj.vm.WrenVM;

@Deprecated
public enum Code {
    CODE_CONSTANT,
    CODE_NULL,
    CODE_FALSE,
    CODE_TRUE,
    CODE_LOAD_LOCAL_0,
    CODE_LOAD_LOCAL_1,
    CODE_LOAD_LOCAL_2,
    CODE_LOAD_LOCAL_3,
    CODE_LOAD_LOCAL_4,
    CODE_LOAD_LOCAL_5,
    CODE_LOAD_LOCAL_6,
    CODE_LOAD_LOCAL_7,
    CODE_LOAD_LOCAL_8,
    CODE_LOAD_LOCAL,
    CODE_STORE_LOCAL,
    CODE_LOAD_UPVALUE,
    CODE_STORE_UPVALUE,
    CODE_LOAD_MODULE_VAR,
    CODE_STORE_MODULE_VAR,
    CODE_LOAD_FIELD_THIS,
    CODE_STORE_FIELD_THIS,
    CODE_LOAD_FIELD,
    CODE_STORE_FIELD,
    CODE_POP,
    CODE_DUP,
    CODE_CALL_0,
    CODE_CALL_1,
    CODE_CALL_2,
    CODE_CALL_3,
    CODE_CALL_4,
    CODE_CALL_5,
    CODE_CALL_6,
    CODE_CALL_7,
    CODE_CALL_8,
    CODE_CALL_9,
    CODE_CALL_10,
    CODE_CALL_11,
    CODE_CALL_12,
    CODE_CALL_13,
    CODE_CALL_14,
    CODE_CALL_15,
    CODE_CALL_16,
    CODE_SUPER_0,
    CODE_SUPER_1,
    CODE_SUPER_2,
    CODE_SUPER_3,
    CODE_SUPER_4,
    CODE_SUPER_5,
    CODE_SUPER_6,
    CODE_SUPER_7,
    CODE_SUPER_8,
    CODE_SUPER_9,
    CODE_SUPER_10,
    CODE_SUPER_11,
    CODE_SUPER_12,
    CODE_SUPER_13,
    CODE_SUPER_14,
    CODE_SUPER_15,
    CODE_SUPER_16,
    CODE_JUMP,
    CODE_LOOP,
    CODE_JUMP_IF,
    CODE_AND,
    CODE_OR,
    CODE_CLOSE_UPVALUE,
    CODE_RETURN,
    CODE_CLOSURE,
    CODE_CONSTRUCT,
    CODE_FOREIGN_CONSTRUCT,
    CODE_CLASS,
    CODE_FOREIGN_CLASS,
    CODE_METHOD_INSTANCE,
    CODE_METHOD_STATIC,
    CODE_LOAD_MODULE,
    CODE_IMPORT_VARIABLE,
    CODE_END;

    public byte toByte() {
        return (byte)ordinal();
    }

    public static Code fromByte(byte index) {
        return values()[index];
    }

    public Code add(int x) {
        int newX = ordinal() + x;
        return values()[newX];
    }
}
