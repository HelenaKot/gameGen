package com.fancytank.gamegen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.editor.PickBrushActivity;
import com.fancytank.gamegen.editor.TileActivity;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.BlockButton;
import com.fancytank.gamegen.programming.BlocksExpendableList;
import com.fancytank.gamegen.programming.Workspace;
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
                EventBus.getDefault().post(ScreenManager.currentScreen);
                break;
            case SETUP_FINISHED:
                setupScreen(ScreenManager.currentScreen);
                break;
        }
    }

    private void setupScreen(ScreenEnum screen) throws IOException, ClassNotFoundException {
        setToolsLayerContent(screen);
        switch (screen) {
            case DESIGN_SCREEN:
                getDataFromManager(gdxFrame.getRootView());
                DesignScreen.loadBoard(BoardManager.get("default")); // todo
                break;
            case EDITOR_SCREEN:
                list.populateList();
                Workspace.populateWorkspace(getDataFromManager(gdxFrame.getRootView()).blocks);
                break;
            case GAME_SCREEN:
                GameScreen.loadGame(getDataFromManager(gdxFrame.getRootView()).blocks, BoardManager.get("default")); //todo
                break;
        }
    }

    @Subscribe
    public void onEvent(BlockButton.OpenLayer event) {
        slidingLayer.openLayer(true);
    }

    private SaveInstance getDataFromManager(View view) throws IOException, ClassNotFoundException {
        return DataManager.loadWorkspace(view.getContext().getFilesDir().getAbsolutePath(), saveName);
    }

    private void initDesignButtons(View designButtons) {
        designButtons.findViewById(R.id.button_programming).setOnClickListener(
                v -> {
                    BoardManager.addBoard("default", ((EditorMap) MapManager.getMap()).getMapAsBoard()); //todo
                    EventBus.getDefault().post(ScreenEnum.EDITOR_SCREEN);
                });
        designButtons.findViewById(R.id.button_new_class).setOnClickListener(
                v -> {
                    Intent intent = new Intent(getContext(), TileActivity.class);
                    getContext().startActivity(intent);
                });
        designButtons.findViewById(R.id.button_paint).setOnClickListener(
                v -> {
                    Intent intent = new Intent(getContext(), PickBrushActivity.class);
                    getContext().startActivity(intent);
                }
        );
    }

    private void initDebugTools(View debugButtons) {
        debugText = (TextView) findViewById(R.id.debug_text);
        debugButtons.findViewById(R.id.button_debug_log).setOnClickListener(
                v -> debugText.setText(Workspace.getDebugLog()));
        debugButtons.findViewById(R.id.button_debug_connectors).setOnClickListener(
                v -> ConnectionArea.debug = !ConnectionArea.debug);
        debugButtons.findViewById(R.id.button_debug_save).setOnClickListener(
                v -> DataManager.saveWorkspace(v.getContext().getFilesDir().getAbsolutePath(), saveName, Workspace.getWorkspaceItemsToSave()));
        debugButtons.findViewById(R.id.button_debug_load).setOnClickListener(
                v -> {
                    try {
                        Workspace.populateWorkspace(getDataFromManager(v).blocks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        debugButtons.findViewById(R.id.button_debug_delete).setOnClickListener(
                v -> Workspace.clearWorkspace());
        debugButtons.findViewById(R.id.button_debug_test).setOnClickListener(
                v -> {
                    if (ScreenManager.currentScreen == ScreenEnum.GAME_SCREEN)
                        EventBus.getDefault().post(ScreenEnum.EDITOR_SCREEN);
                    else
                        EventBus.getDefault().post(ScreenEnum.GAME_SCREEN);
                });
        debugButtons.findViewById(R.id.button_debug_design).setOnClickListener(
                v -> EventBus.getDefault().post(ScreenEnum.DESIGN_SCREEN));
    }

    //todo to wszystko zniknie, jak debug nie będzie potrzebny
    private void setToolsLayerContent(ScreenEnum screen) {
        SlidingLayer toolsLayer = (SlidingLayer) findViewById(R.id.tools_layer);
        runOnUiThread(() -> {
            toolsLayer.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(getResId(screen), toolsLayer);
            switch (screen) {
                case DESIGN_SCREEN:
                    initDesignButtons(toolsLayer);
                    break;
                case EDITOR_SCREEN:
                case GAME_SCREEN:
                    initDebugTools(toolsLayer);
                    break;
            }
        });
    }

    private int getResId(ScreenEnum screen) {
        switch (screen) {
            case DESIGN_SCREEN:
                return R.layout.toolset_design;
            case EDITOR_SCREEN:
                return R.layout.toolset_debug;
            default:
                return R.layout.toolset_debug;
        }
    }
}
