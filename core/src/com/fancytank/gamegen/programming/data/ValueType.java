package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public enum ValueType implements Serializable {
    // todo do połączeń między bloczkami VV
    COLOR(MethodType.COLOR_SETTER),
    NUMBER,
    INT_NUMBER,
    CLASS_NAME(MethodType.BLOCK_SETTR),
    METHOD;

    MethodType expectedMethod;

    ValueType() {
    }

    ValueType(MethodType expectedMethod) {
        this.expectedMethod = expectedMethod;
    }

    public boolean hasExpectedMethod() {
        return expectedMethod != null;
    }

    public MethodType getExpectedMethod() {
        return expectedMethod;
    }
}
