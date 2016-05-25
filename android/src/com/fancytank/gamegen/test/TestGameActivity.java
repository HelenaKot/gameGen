package com.fancytank.gamegen.test;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.AndroidGameGenerator;
import com.fancytank.gamegen.R;

public class TestGameActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_game);

        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new AndroidGameGenerator());
        contentFrame.addView(gdxView);
    }
}
