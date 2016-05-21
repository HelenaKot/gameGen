package com.fancytank.gamegen;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.programming.Workspace;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AndroidLauncher extends AndroidApplication {
    private SlidingLayer slidingLayer;
    private BlocksExpendableList list;
    private TextView debugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        debugText = (TextView) findViewById(R.id.debug_text);
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
    }

    @Subscribe
    public void onEvent(BlockButton.OpenLayer event) {
        slidingLayer.openLayer(true);
    }

    public void OnDebugClick(View view) {
        debugText.setText(Workspace.getDebugLog());
    }
}
