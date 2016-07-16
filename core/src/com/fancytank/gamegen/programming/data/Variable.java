package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public class Variable implements Serializable {
    public String value;
    public ValueType valueType;

    public Variable(String value, ValueType valueType) {
        this.value = value;
        this.valueType = valueType;
    }
}