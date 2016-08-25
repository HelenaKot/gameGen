package com.fancytank.gamegen.programming;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.MethodType;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.dialog.DialogSpawner;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.ArrayList;

public class BlocksExpendableList {
    private ExpandableListView expandableList;
    private BlockExpendableListAdapter adapter;
    private Activity activity;

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<BlockPatternHolder[]> childItems = new ArrayList<>();

    public BlocksExpendableList(ExpandableListView expandableList, Activity context) {
        this.expandableList = expandableList;
        activity = context;
        setListView();

        adapter = new BlockExpendableListAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        expandableList.setAdapter(adapter);
    }

    private void setListView() {
        expandableList.setClickable(true);
        expandableList.setDividerHeight(2);
    }

    public void populateList() {
        if (parentItems.size() == 0) {
            setGroupParents();
            setChildData();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setGroupParents() {
        parentItems.add("Tile");
        parentItems.add("Logic");
        parentItems.add("Variables");
        parentItems.add("Getters");
    }

    private void setChildData() {
        BlockPatternHolder[] tilesPatterns = {
                new BlockPatternHolder(new BlockActorPattern("on click", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "when this object is PRESSED").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, "do"),
                        new InputFragment(InputType.SOCKET, "ON_PRESS_SOCKET").setExpectedValue(ValueType.METHOD)}, BlockShape.ACTION_LISTENER), Color.PURPLE)),

                new BlockPatternHolder(new BlockActorPattern("on time", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "Trigger on this amount of ticks").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "for class").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.SOCKET, "ON_PRESS_SOCKET").setExpectedValue(ValueType.METHOD)}, BlockShape.TIMER), Color.PURPLE)),

                new BlockPatternHolder(new BlockActorPattern("tile type", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "generic")}, BlockShape.VARIABLE), Color.SKY), DialogSpawner.DIALOG_PICK_CLASS),
/*
                new CustomBlockPattern("move object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "move self"),
                        new InputFragment(InputType.VARIABLE, "vertically").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "horizontally").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE),
*/
                new BlockPatternHolder(new BlockActorPattern("replace", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "replace with").setExpectedValue(ValueType.CLASS_NAME)}, BlockShape.CHAIN_METHOD), Color.ORANGE)),

                new BlockPatternHolder(new BlockActorPattern("spawn object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "spawn new").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, ""),
                        new InputFragment(InputType.VARIABLE, "at x").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "at y").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE)),

                new BlockPatternHolder(new BlockActorPattern("change color", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "change color to").setExpectedValue(ValueType.COLOR)}, BlockShape.CHAIN_METHOD), Color.ORANGE)),
/*
                new CustomBlockPattern("delete object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "delete self")}, BlockShape.LAST_METHOD), Color.RED),*/
        };
        childItems.add(tilesPatterns);

        BlockPatternHolder[] logicPatterns = {
                new BlockPatternHolder(new BlockActorPattern("if", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "if true, then").setExpectedValue(ValueType.BOOLEAN),
                        new InputFragment(InputType.SOCKET, "if body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.IF_STATEMENT), Color.YELLOW)),

                new BlockPatternHolder(new BlockActorPattern("if else", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "if true, then").setExpectedValue(ValueType.BOOLEAN),
                        new InputFragment(InputType.SOCKET, "if body").setExpectedValue(ValueType.METHOD),
                        new InputFragment(InputType.DUMMY, "else").setExpectedValue(ValueType.BOOLEAN),
                        new InputFragment(InputType.SOCKET, "if body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.IF_STATEMENT), Color.YELLOW)),

                new BlockPatternHolder(new BlockActorPattern("loop", null, Color.YELLOW), DialogSpawner.DIALOG_LOOP),

                new BlockPatternHolder(new BlockActorPattern("compare", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "compare").setExpectedValue(ValueType.ANY),
                        new InputFragment(InputType.VARIABLE, "      ").setExpectedValue(ValueType.ANY)},
                        BlockShape.VARIABLE).setExpectedMethod(MethodType.COMPARE_STATEMENT), Color.SKY), DialogSpawner.DIALOG_COMPARATOR),

                new BlockPatternHolder(new BlockActorPattern("TRUE", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "true")}, BlockShape.VARIABLE), Color.BLUE).setValue("true", ValueType.BOOLEAN)),

                new BlockPatternHolder(new BlockActorPattern("FALSE", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "false")}, BlockShape.VARIABLE), Color.BLUE).setValue("false", ValueType.BOOLEAN)),

                new BlockPatternHolder(new BlockActorPattern("+", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "+").setExpectedValue(ValueType.NUMBER),
                        new InputFragment(InputType.VARIABLE, " ").setExpectedValue(ValueType.NUMBER)},
                        BlockShape.VARIABLE).setExpectedMethod(MethodType.SUM), Color.SKY)),

        };
        childItems.add(logicPatterns);

        BlockPatternHolder[] variablesPatterns = {
                new BlockPatternHolder(new BlockActorPattern("new variable", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "var")}, BlockShape.VARIABLE_DECLARATION), Color.PINK), DialogSpawner.DIALOG_INIT_VAR),
                new BlockPatternHolder(new BlockActorPattern("get variable", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "get var")}, BlockShape.VARIABLE), Color.PINK), DialogSpawner.DIALOG_GET_VAR),
                new BlockPatternHolder(new BlockActorPattern("update variable", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "set variable")}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.VARIABLE_SETTER), Color.PINK), DialogSpawner.DIALOG_SET_VAR),
                new BlockPatternHolder(new BlockActorPattern("number [const]", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "0")}, BlockShape.VARIABLE), Color.SKY), DialogSpawner.DIALOG_NUMBER),
                new BlockPatternHolder(new BlockActorPattern("color [const]", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "#eeeeee")}, BlockShape.VARIABLE), Color.ORANGE), DialogSpawner.DIALOG_COLOR),
        };
        childItems.add(variablesPatterns);

        BlockPatternHolder[] getterPatterns = {
                new BlockPatternHolder(new BlockActorPattern("get color", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "get color")}, BlockShape.VARIABLE).setVariable(new Variable("c", ValueType.COLOR)).setExpectedMethod(MethodType.GETTER), Color.PINK)),
                new BlockPatternHolder(new BlockActorPattern("get x", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "get x")}, BlockShape.VARIABLE).setVariable(new Variable("x", ValueType.INT_NUMBER)).setExpectedMethod(MethodType.GETTER), Color.PINK)),
                new BlockPatternHolder(new BlockActorPattern("get y", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "get y")}, BlockShape.VARIABLE).setVariable(new Variable("y", ValueType.INT_NUMBER)).setExpectedMethod(MethodType.GETTER), Color.PINK)),
        };
        childItems.add(getterPatterns);

        BlockActorPattern[] loopsPatterns = {
                new BlockActorPattern("LOOP1", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "loop statement while").setExpectedValue(ValueType.BOOLEAN),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.LOOP_WHILE), Color.YELLOW),
                new BlockActorPattern("LOOP2", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "loop for each").setExpectedValue(ValueType.ANY),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.LOOP_FOR), Color.YELLOW),
                new BlockActorPattern("LOOP3", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "loop N times"),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD)
                        .setExpectedMethod(MethodType.LOOP_FOR), Color.YELLOW),
        };

        DialogSpawner.setLoopPatterns(loopsPatterns);


    }


}
