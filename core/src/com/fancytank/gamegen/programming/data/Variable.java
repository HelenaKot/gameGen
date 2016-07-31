package com.fancytank.gamegen.programming.data;

import java.io.Serializable;

public class Variable implements Serializable {
    public ValueType valueType;
    String value;
    private static final long serialVersionUID = 1233613063064497050L;

    public Variable(String value, ValueType valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public Variable(Variable variable) {
        value = variable.value;
        valueType = variable.valueType;
    }

    public String getDirectValue() {
        return value;
    }

    public String getValue() {
        if (valueType != ValueType.VARIABLE)
            return value;
        else
            return VariableList.get(value).value;
    }

    public void setValue(String value) {
        if (valueType != ValueType.VARIABLE)
            this.value = value;
        else
            VariableList.put(this.value, value, ValueType.ANY);
    }

    @Override
    public String toString() {
        return value;
    }
}