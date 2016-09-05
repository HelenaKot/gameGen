package com.fancytank.gamegen.programming.data;

public enum ValueType {
    BOOLEAN(),
    ANY(),
    COLOR(MethodType.COLOR_SETTER),
    NUMBER(),
    INT_NUMBER(),
    CLASS_NAME(MethodType.BLOCK_SETTER),
    METHOD(),
    VARIABLE(),
    SCREEN(MethodType.SCREEN_SWAPPER);

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
            usedValues = new ValueType[]{INT_NUMBER, BOOLEAN, COLOR, NUMBER, CLASS_NAME, METHOD, SCREEN};
            valueNames = new String[]{"integer", "boolean", "color", "number", "class", "method", "screen"};
        }
    }
}
