package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public class Variable implements Serializable {
    public String value;
    public ValueType valueType;
    private static final long serialVersionUID = 1233613063064497050L;

    public Variable(String value, ValueType valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return value;
    }
}