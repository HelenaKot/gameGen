package com.fancytank.gamegen.programming;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.blocks.CustomBlockPattern;
import com.fancytank.gamegen.programming.blocks.PredefinedBlockPattern;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockShape;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.ArrayList;

public class BlocksExpendableList {
    private ExpandableListView expandableList;
    private BlockExpendableListAdapter adapter;
    private Activity activity;

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<BlockActorPattern[]> childItems = new ArrayList<>();

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
        parentItems.add("Input");
        parentItems.add("Variable");
        parentItems.add("Logic");
        parentItems.add("Methods");
        parentItems.add("Collection");
        parentItems.add("Derp");
    }

    private void setChildData() {
        BlockActorPattern[] inputPattern = {
                new CustomBlockPattern("on click", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.SOCKET, "ON_PRESS_SOCKET"),
                        new InputFragment(InputType.DUMMY, "do"),
                        new InputFragment(InputType.VARIABLE, "when this object is pressed")}, BlockShape.ENCLOSED), Color.PURPLE)
        };
        childItems.add(inputPattern);

        BlockActorPattern[] variablePattern = {
                new CustomBlockPattern("object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "PLACEHODLER")}, BlockShape.VARIABLE), Color.CORAL),
                new CustomBlockPattern("number", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "PLACEHODLER")}, BlockShape.VARIABLE), Color.SKY),
                new CustomBlockPattern("name", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "PLACEHODLER")}, BlockShape.VARIABLE), Color.TEAL),
                new CustomBlockPattern("color", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "PLACEHODLER")}, BlockShape.VARIABLE), Color.SKY)
        };
        childItems.add(variablePattern);

        BlockActorPattern[] logicPattern = {
                new CustomBlockPattern("if statement", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.SOCKET, "IF_SOCKET"),
                        new InputFragment(InputType.DUMMY, "do"),
                        new InputFragment(InputType.VARIABLE, "is equal to this"),
                        new InputFragment(InputType.VARIABLE, "if this object")}, BlockShape.CHAIN_FUNCTION), Color.TEAL),
        };
        childItems.add(logicPattern);

        BlockActorPattern[] methodsPattern = {
                new CustomBlockPattern("move object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "vertically"),
                        new InputFragment(InputType.VARIABLE, "horizontally"),
                        new InputFragment(InputType.DUMMY, ""),
                        new InputFragment(InputType.VARIABLE, "move this object")}, BlockShape.CHAIN_FUNCTION), Color.ORANGE),
                new CustomBlockPattern("set color", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "to"),
                        new InputFragment(InputType.VARIABLE, "set color of")}, BlockShape.CHAIN_FUNCTION), Color.ORANGE),
                new CustomBlockPattern("spawn object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "create new instance of")}, BlockShape.CHAIN_FUNCTION), Color.RED),
                new CustomBlockPattern("kill object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "remove this object from existence")}, BlockShape.CHAIN_FUNCTION), Color.RED),
        };
        childItems.add(methodsPattern);

        BlockActorPattern[] collectionPattern = {

        };
        childItems.add(collectionPattern);

        BlockActorPattern[] derpPatterns = {
                PredefinedBlockPattern.TEXT_VARIABLE,
                PredefinedBlockPattern.PRINT,
                new CustomBlockPattern("function", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "LOREM IPSUM")}, BlockShape.CHAIN_FUNCTION), Color.CORAL),
                new CustomBlockPattern("socket input", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.SOCKET, "PLACEHOLDER"), new InputFragment(InputType.DUMMY, "PLACEHOLDER")}, BlockShape.ENCLOSED), Color.PURPLE),
                new CustomBlockPattern("sockets", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.SOCKET, "PLACEHOLDER"), new InputFragment(InputType.VARIABLE, "PLACEHOLDER"),
                        new InputFragment(InputType.SOCKET, "PLACEHOLDER"), new InputFragment(InputType.DUMMY, "PLACEHOLDER")}, BlockShape.CHAIN_FUNCTION), Color.PINK)

        };
        childItems.add(derpPatterns);
    }
}
