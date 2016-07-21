package com.fancytank.gamegen.design;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.fancytank.gamegen.R;
import com.wunderlist.slidinglayer.SlidingLayer;

public class DesignActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_design);

        SlidingLayer toolsPanel = (SlidingLayer) findViewById(R.id.tool_panel);
        toolsPanel.openPreview(false);

    }
}
