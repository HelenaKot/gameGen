package com.fancytank.gamegen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {
    private SlidingLayer slidingLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new AndroidGameGenerator());
        contentFrame.addView(gdxView);
        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);
        slidingLayer.closeLayer(true);

        setGroupParents();
        setChildData();

        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.drawer_list);
        expandableList.setClickable(true);
        expandableList.setDividerHeight(2);

        BlockExpendableListAdapter adapter = new BlockExpendableListAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
    }

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    public void setGroupParents() {
        parentItems.add("Android");
        parentItems.add("Core Java");
        parentItems.add("Desktop Java");
        parentItems.add("Enterprise Java");
    }

    public void setChildData() {

        // Android
        ArrayList<String> child = new ArrayList<String>();
        child.add("Core");
        child.add("Games");
        childItems.add(child);

        // Core Java
        child = new ArrayList<String>();
        child.add("Apache");
        child.add("Applet");
        child.add("AspectJ");
        child.add("Beans");
        child.add("Crypto");
        childItems.add(child);

        // Desktop Java
        child = new ArrayList<String>();
        child.add("Accessibility");
        child.add("AWT");
        child.add("ImageIO");
        child.add("Print");
        childItems.add(child);

        // Enterprise Java
        child = new ArrayList<String>();
        child.add("EJB3");
        child.add("GWT");
        child.add("Hibernate");
        child.add("JSP");
        childItems.add(child);
    }

}
