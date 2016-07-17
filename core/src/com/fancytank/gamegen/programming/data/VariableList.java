package com.fancytank.gamegen.programming.data;

import java.util.HashMap;
import java.util.Set;

public class VariableList {
    HashMap<String, Variable> variables;
    private static VariableList instance;

    VariableList() {
        variables = new HashMap<String, Variable>();
        instance = this;
    }

    public static String[] getKeys() {
        Set<String> keyset = getInstance().variables.keySet();
        return keyset.toArray(new String[keyset.size()]);
    }

    public static VariableList getInstance() {
        if (instance == null)
            instance = new VariableList();
        return instance;
    }

    public static Variable get(String name) {
        return getInstance().variables.get(name);
    }

    public static void put(String name, Variable variable) {
        if (!getInstance().variables.containsKey(name))
            instance.variables.put(name, variable);
        else
            updateValue(name, variable);
    }

    public static void put(String name, String value, ValueType type) {
        put(name, new Variable(value, type));
    }

    static void updateValue(String name, Variable variable) {
        if (getInstance().variables.get(name).valueType == variable.valueType)
            instance.variables.get(name).value = variable.value;
        else
            System.out.println("Variable " + name + " is a different type [" + instance.variables.get(name).valueType + " != " + variable.valueType + "]");
    }
}
