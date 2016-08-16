package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public enum ValueType implements Serializable {
    BOOLEAN(),
    ANY(),
    COLOR(MethodType.COLOR_SETTER),
    NUMBER(),
    INT_NUMBER(),
    CLASS_NAME(MethodType.BLOCK_SETTER),
    METHOD(),
    VARIABLE();

    private static final long serialVersionUID = 1233613063064496950L;
    MethodType expectedMethod;

    ValueType() {
    }

    ValueType(MethodType expectedMethod) {
        this.expectedMethod = expectedMethod;
    }

    static ValueType[] usedValues;
    static String[] valueNames;

    public static ValueType[] getValuesList() {
        initValues();
        return usedValues;
    }

    public static String[] getValueStrings() {
        initValues();
        return valueNames;
    }

    private static void initValues() {
        if (usedValues == null) {
            usedValues = new ValueType[]{BOOLEAN, COLOR, NUMBER, INT_NUMBER, CLASS_NAME, METHOD};
            valueNames = new String[]{"boolean", "color", "number", "integer", "class", "method"};
        }
    }
}
