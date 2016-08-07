package com.fancytank.gamegen.programming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.GameScreen;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.R;
import com.fancytank.gamegen.ScreenEnum;
import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class ProgrammingActivity extends AndroidApplication {
    private FrameLayout contentFrame;
    private SlidingLayer slidingLayer;
    private BlocksExpendableList list;
    private TextView debugText;
    private String saveName = "untitled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_programming);

        init();
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        if (intent.hasExtra("saveName"))
            saveName = intent.getExtras().getString("saveName");
    }

    private void init() {
        debugText = (TextView) findViewById(R.id.debug_text);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        contentFrame.addView(initializeForView(new MainGdx()));
        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);
        list = new BlocksExpendableList((ExpandableListView) findViewById(R.id.drawer_list), this);
    }

    @Subscribe
    public void onEvent(MainGdx.AppStatus status) throws IOException, ClassNotFoundException {
        switch (status) {
            case GDX_INIT_FINISHED:
                EventBus.getDefault().post(MainGdx.currentScreen);
                break;
            case SETUP_FINISHED:
                setupScreen(MainGdx.currentScreen);
                break;
        }
    }

    private void setupScreen(ScreenEnum screen) throws IOException, ClassNotFoundException {
        switch (screen) {
            case DESIGN_SCREEN:
                break;
            case EDITOR_SCREEN:
                list.populateList();
                loadWorkspace(contentFrame.getRootView());
                break;
            case GAME_SCREEN:
                GameScreen.loadGame(loadDataFromFile(contentFrame.getRootView()));
                break;
        }
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

    public void saveWorkspace(View view) throws IOException {
        DataManager.saveBlocks(view.getContext().getFilesDir().getAbsolutePath(), saveName, Workspace.getWorkspaceItemsToSave());
    }

    public void loadWorkspace(View view) throws IOException, ClassNotFoundException {
        Workspace.loadBlocks(loadDataFromFile(view));
    }

    private ProgrammingBlockSavedInstance[] loadDataFromFile(View view) throws IOException, ClassNotFoundException {
        return DataManager.loadBlocks(view.getContext().getFilesDir().getAbsolutePath(), saveName);
    }

    public void deleteAll(View view) {
        Workspace.clearWorkspace();
    }

    //todo refactoringplz
    public void switchScreen(View v) {
        if (MainGdx.currentScreen == ScreenEnum.GAME_SCREEN)
            EventBus.getDefault().post(ScreenEnum.EDITOR_SCREEN);
        else
            EventBus.getDefault().post(ScreenEnum.GAME_SCREEN);
    }
}
