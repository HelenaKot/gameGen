package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public enum MethodType implements Serializable {
    BLOCK_SETTER,
    COLOR_SETTER,
    VARIABLE_SETTER,
    COMPARE_STATEMENT,
    LOOP_WHILE,
    LOOP_FOR,
    IF_STATEMENT,
    GETTER,
    SUM,
    UNSPECIFIED;

    private static final long serialVersionUID = 1233613063064496934L;
}
