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
        parentItems.add("Sample");
        parentItems.add("Logic");
        parentItems.add("Input");
        parentItems.add("Derp");
    }

    private void setChildData() {
        BlockActorPattern[] samplePatterns = {};
        childItems.add(samplePatterns);

        BlockActorPattern[] logicPatterns = {PredefinedBlockPattern.TEXT_VARIABLE};
        childItems.add(logicPatterns);

        BlockActorPattern[] inputPatterns = {PredefinedBlockPattern.TEXT_VARIABLE};
        childItems.add(inputPatterns);

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
