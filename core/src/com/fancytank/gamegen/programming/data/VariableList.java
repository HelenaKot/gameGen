package com.fancytank.gamegen.programming.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class VariableList implements Serializable {
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

    public static String getString(String name) {
        if (doExist(name))
            return get(name).value;
        return null;
    }

    public static Integer getInt(String name) {
        return Integer.parseInt(getString(name));
    }

    private static boolean doExist(String key) {
        return getInstance().variables.containsKey(key);
    }

    public static void put(String name, Variable variable) {
        put(name, variable.value, variable.valueType);
    }

    public static void put(String name, String value, ValueType type) {
        if (!getInstance().variables.containsKey(name))
            instance.variables.put(name, new Variable(value, type));
        else
            updateValue(name, value, type);
    }

    static void updateValue(String name, String value, ValueType type) {
        if (getInstance().variables.get(name).valueType == type)
            instance.variables.get(name).value = value;
        else
            System.out.println("Variable " + name + " is a different type [" + instance.variables.get(name).valueType + " != " + type + "]");
    }
}
