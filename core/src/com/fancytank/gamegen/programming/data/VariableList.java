package com.fancytank.gamegen.programming.data;

import java.util.HashMap;
import java.util.Set;

public class VariableList {
    HashMap<String, Variable> variables;
    private static VariableList instance;

    VariableList() {
        variables = new HashMap<>();
        instance = this;
    }

    public static String[] getKeys() {
        Set<String> keyset = getInstance().variables.keySet();
        return keyset.toArray(new String[keyset.size()]);
    }

    public static Variable get(String name) {
        return getInstance().variables.get(name);
    }

    public static void put(String name, Variable variable) {
        if (!getInstance().variables.containsKey(name))
            instance.variables.put(name, new Variable(variable));
        else
            updateValue(name, variable);
    }

    public static void put(String name, String value, ValueType type) {
        put(name, new Variable(value, type));
    }

    private static VariableList getInstance() {
        if (instance == null)
            instance = new VariableList();
        return instance;
    }

    private static void updateValue(String name, Variable variable) {
        instance.variables.get(name).setValue(variable.getValue());
    }
}
