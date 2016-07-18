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
        parentItems.add("Demo");
        parentItems.add("Logic");
        parentItems.add("Variables");
    }

    private void setChildData() {
        BlockPatternHolder[] demoPatterns = {
                new BlockPatternHolder(new BlockActorPattern("on click", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "when this object is PRESSED").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, "do"),
                        new InputFragment(InputType.SOCKET, "ON_PRESS_SOCKET").setExpectedValue(ValueType.METHOD)}, BlockShape.ACTION_LISTENER), Color.PURPLE)),

                new BlockPatternHolder(new BlockActorPattern("empty space", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "empty space")}, BlockShape.VARIABLE).setVariable("empty", ValueType.CLASS_NAME), Color.SKY)),
                new BlockPatternHolder(new BlockActorPattern("placeholder actor", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "placeholder")}, BlockShape.VARIABLE).setVariable("generic", ValueType.CLASS_NAME), Color.SKY)),
/*
                new CustomBlockPattern("move object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "move self"),
                        new InputFragment(InputType.VARIABLE, "vertically").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "horizontally").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE),
*/

                new BlockPatternHolder(new BlockActorPattern("NUMBER", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "0")}, BlockShape.VARIABLE), Color.SKY), SpawnBlockDialog.DIALOG_NUMBER),

                new BlockPatternHolder(new BlockActorPattern("spawn object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "spawn new").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, ""),
                        new InputFragment(InputType.VARIABLE, "at x").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "at y").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE)),

                new BlockPatternHolder(new BlockActorPattern("change color", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "change color to").setExpectedValue(ValueType.COLOR)}, BlockShape.CHAIN_METHOD), Color.ORANGE)),

                new BlockPatternHolder(new BlockActorPattern("COLOR", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "#eeeeee")}, BlockShape.VARIABLE), Color.ORANGE), SpawnBlockDialog.DIALOG_COLOR),

/*
                new CustomBlockPattern("delete object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "delete self")}, BlockShape.LAST_METHOD), Color.RED),*/
        };
        childItems.add(demoPatterns);

        BlockPatternHolder[] logicPatterns = {
                new BlockPatternHolder(new BlockActorPattern("IF", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "if STATEMENT ").setExpectedValue(ValueType.BOOLEAN).setExpectedMethod(MethodType.IF_STATEMENT),
                        new InputFragment(InputType.SOCKET, "if body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD), Color.YELLOW)),

                new BlockPatternHolder(new BlockActorPattern("LOOP", null, Color.YELLOW), SpawnBlockDialog.DIALOG_LOOP),

                // todo make dialog for  <>= ?
                new BlockPatternHolder(new BlockActorPattern("equals", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "are equal").setExpectedValue(ValueType.ANY),
                        new InputFragment(InputType.VARIABLE, " ").setExpectedValue(ValueType.ANY)}, BlockShape.VARIABLE), Color.BLUE)),

                new BlockPatternHolder(new BlockActorPattern("TRUE", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "true").setExpectedValue(ValueType.BOOLEAN)}, BlockShape.VARIABLE), Color.BLUE)), //todo

                new BlockPatternHolder(new BlockActorPattern("FALSE", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "false")}, BlockShape.VARIABLE), Color.BLUE).setValue("false", ValueType.BOOLEAN)),

        };
        childItems.add(logicPatterns);

        BlockPatternHolder[] variablesPatterns = {
                new BlockPatternHolder(new BlockActorPattern("NEW VARIABLE", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "var")}, BlockShape.VARIABLE_DECLARATION), Color.PINK), SpawnBlockDialog.DIALOG_INIT_VAR),
                new BlockPatternHolder(new BlockActorPattern("get variable", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "get var")}, BlockShape.VARIABLE), Color.PINK), SpawnBlockDialog.DIALOG_GET_VAR),
                new BlockPatternHolder(new BlockActorPattern("update variable", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "update var").setExpectedMethod(MethodType.VARIABLE_SETTER)}, BlockShape.CHAIN_METHOD), Color.PINK), SpawnBlockDialog.DIALOG_SET_VAR)
        };
        childItems.add(variablesPatterns);

        BlockActorPattern[] loopsPatterns = {
                new BlockActorPattern("LOOP1", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "loop statement while").setExpectedValue(ValueType.BOOLEAN).setExpectedMethod(MethodType.LOOP_WHILE),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD), Color.YELLOW),
                new BlockActorPattern("LOOP2", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "loop for each").setExpectedValue(ValueType.ANY).setExpectedMethod(MethodType.LOOP_FOR),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD), Color.YELLOW),
                new BlockActorPattern("LOOP3", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "loop N times").setExpectedMethod(MethodType.LOOP_FOR),
                        new InputFragment(InputType.SOCKET, "loop body").setExpectedValue(ValueType.METHOD)}, BlockShape.CHAIN_METHOD), Color.YELLOW),
        };

        SpawnBlockDialog.setLoopPaterns(loopsPatterns);


    }


}
