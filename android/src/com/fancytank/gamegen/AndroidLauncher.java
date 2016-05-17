package com.fancytank.gamegen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.wunderlist.slidinglayer.SlidingLayer;

public class AndroidLauncher extends AndroidApplication {
    private ListView listOfBlocks;
    private SlidingLayer slidingLayer;
    private String[] mRandomList = {"derp", "herp"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new AndroidGameGenerator());

        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);

        contentFrame.addView(gdxView);
        setUpListOfBlocks();
    }

    private void setUpListOfBlocks() {
        listOfBlocks = (ListView) findViewById(R.id.drawer_list);
        listOfBlocks.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mRandomList));
        listOfBlocks.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Log.e("LOG", "selected item- " + position);
        listOfBlocks.setItemChecked(position, true);
        slidingLayer.closeLayer(true);
    }

}
