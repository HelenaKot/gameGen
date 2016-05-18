package com.fancytank.gamegen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AndroidLauncher extends AndroidApplication {
    private SlidingLayer slidingLayer;
    private BlocksExpendableList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new AndroidGameGenerator());
        contentFrame.addView(gdxView);
        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);
        slidingLayer.closeLayer(true);
        list = new BlocksExpendableList((ExpandableListView) findViewById(R.id.drawer_list), this);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(AndroidGameGenerator.SetUpFinished event) {
        list.populateList();
        Log.e("LOG", "event is here");
    }

}
