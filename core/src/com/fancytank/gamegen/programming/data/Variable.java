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

    public int compareTo(Variable var1) {
        if ((valueType == ValueType.NUMBER || valueType == ValueType.INT_NUMBER)
                && (var1.valueType == ValueType.NUMBER || var1.valueType == ValueType.INT_NUMBER))
            return Float.compare(Float.parseFloat(value), Float.parseFloat(var1.value));
        else
            return value.compareTo(var1.value);
    }

    public int getInt() {
        return Integer.parseInt(getValue());
    }

    @Override
    public String toString() {
        return value;
    }
}