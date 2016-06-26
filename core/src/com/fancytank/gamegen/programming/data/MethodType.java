package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public enum MethodType implements Serializable {
    BLOCK_SETTR,
    COLOR_SETTER,
    LOGIC_STATEMENT,
    CONDITIONED_STATEMENT;

    private static final long serialVersionUID = 1233613063064496934L;
}
