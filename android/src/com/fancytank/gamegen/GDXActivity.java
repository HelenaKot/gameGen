package com.fancytank.gamegen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.programming.BlocksExpendableList;
import com.fancytank.gamegen.programming.Workspace;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class GDXActivity extends AndroidApplication {
    private FrameLayout gdxFrame;
    private SlidingLayer slidingLayer;
    private BlocksExpendableList list;
    private TextView debugText;
    private String saveName = "untitled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gdx);

        initDebugTools();
        initXML();
        readName();
        EventBus.getDefault().register(this);
    }

    private void initXML() {
        gdxFrame = (FrameLayout) findViewById(R.id.content_frame);
        gdxFrame.addView(initializeForView(new MainGdx()));
        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);
        list = new BlocksExpendableList((ExpandableListView) findViewById(R.id.drawer_list), this);
    }

    private void readName() {
        Intent intent = getIntent();
        if (intent.hasExtra("saveName"))
            saveName = intent.getExtras().getString("saveName");
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
                loadWorkspace(gdxFrame.getRootView());
                break;
            case GAME_SCREEN:
                GameScreen.loadGame(loadDataFromFile(gdxFrame.getRootView()));
                break;
        }
    }

    @Subscribe
    public void onEvent(BlockButton.OpenLayer event) {
        slidingLayer.openLayer(true);
    }

    public void loadWorkspace(View view) throws IOException, ClassNotFoundException {
        Workspace.loadBlocks(loadDataFromFile(view));
    }

    private ProgrammingBlockSavedInstance[] loadDataFromFile(View view) throws IOException, ClassNotFoundException {
        return DataManager.loadBlocks(view.getContext().getFilesDir().getAbsolutePath(), saveName);
    }

    private void initDebugTools() {
        SlidingLayer toolsLayer = (SlidingLayer) findViewById(R.id.tools_layer);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View debugButtons = inflater.inflate(R.layout.toolset_debug, toolsLayer);

        debugText = (TextView) findViewById(R.id.debug_text);
        ((Button) debugButtons.findViewById(R.id.button_debug_log)).setOnClickListener(
                v -> debugText.setText(Workspace.getDebugLog()));
        ((Button) debugButtons.findViewById(R.id.button_debug_connectors)).setOnClickListener(
                v -> ConnectionArea.debug = !ConnectionArea.debug);
        ((Button) debugButtons.findViewById(R.id.button_debug_save)).setOnClickListener(
                v -> DataManager.saveBlocks(v.getContext().getFilesDir().getAbsolutePath(), saveName, Workspace.getWorkspaceItemsToSave()));
        ((Button) debugButtons.findViewById(R.id.button_debug_load)).setOnClickListener(
                v -> {
                    try {
                        loadWorkspace(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        ((Button) debugButtons.findViewById(R.id.button_debug_delete)).setOnClickListener(
                v -> Workspace.clearWorkspace());
        ((Button) debugButtons.findViewById(R.id.button_debug_test)).setOnClickListener(
                v -> {
                    if (MainGdx.currentScreen == ScreenEnum.GAME_SCREEN)
                        EventBus.getDefault().post(ScreenEnum.EDITOR_SCREEN);
                    else
                        EventBus.getDefault().post(ScreenEnum.GAME_SCREEN);
                });
    }
}
