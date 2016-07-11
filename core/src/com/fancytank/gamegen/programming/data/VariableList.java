package com.fancytank.gamegen.programming.data;

import java.io.Serializable;
import java.util.HashMap;

public class VariableList implements Serializable {
    HashMap<String, Variable> variables;
    private static VariableList instance;

    VariableList() {
        variables = new HashMap<String, Variable>();
        instance = this;
    }

    public static void instantiate(VariableList list) {
        if (list != null)
            instance = list;
        else
            list = new VariableList();
    }

    public static VariableList getInstance() {
        return instance;
    }

    public static Variable get(String name) {
        return instance.variables.get(name);
    }

    public static String getString(String name) {
        if (doExist(name))
            return get(name).value;
        return null;
    }

    public static Integer getInt(String name) {
        return Integer.parseInt(getString(name));
    }

    private static boolean doExist(String key) {
        return instance.variables.containsKey(key);
    }

    public static void set(String name, String value, ValueType type) {
        instance.variables.put(name, new Variable(value, type));
    }

    static class Variable implements Serializable {
        String value;
        ValueType valueType;

        Variable(String value, ValueType valueType) {
            this.value = value;
            this.valueType = valueType;
        }
    }
}
