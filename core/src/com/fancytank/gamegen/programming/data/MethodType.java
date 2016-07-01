package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public enum MethodType implements Serializable {
    BLOCK_SETTER,
    COLOR_SETTER,
    LOGIC_STATEMENT,
    LOOP_WHILE,
    LOOP_FOR,
    CONDITIONED_STATEMENT;

    private static final long serialVersionUID = 1233613063064496934L;
}
