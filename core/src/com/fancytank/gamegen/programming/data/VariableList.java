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

    public static VariableList getInstance() {
        if (instance == null)
            instance = new VariableList();
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

    public static void put(String name, String value, ValueType type) {
        if (!instance.variables.containsKey(name))
            instance.variables.put(name, new Variable(value, type));
        else
            update(name, value, type);
    }

    static void update(String name, String value, ValueType type) {
        if (instance.variables.get(name).valueType == type)
            instance.variables.get(name).value = value;
        else
            System.out.println("Variable " + name + " is a different type [" + instance.variables.get(name).valueType + " != " + type + "]");
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
