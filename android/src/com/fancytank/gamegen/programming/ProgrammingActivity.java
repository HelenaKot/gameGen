package com.fancytank.gamegen.programming;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.AndroidGameGenerator;
import com.fancytank.gamegen.R;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProgrammingActivity extends AndroidApplication {
    private SlidingLayer slidingLayer;
    private BlocksExpendableList list;
    private TextView debugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_programming);
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

    public void onDebugClick(View view) {
        debugText.setText(Workspace.getDebugLog());
    }

    public void paintConnectors(View view) {
        ConnectionArea.debug = !ConnectionArea.debug;
    }
}
