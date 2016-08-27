package com.fancytank.gamegen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.editor.PickBrushActivity;
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
    private ExpandableListView drawerList;
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
        drawerList = (ExpandableListView) findViewById(R.id.drawer_list);
        list = new BlocksExpendableList(drawerList, this);
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
        designButtons.findViewById(R.id.button_paint).setOnClickListener(
                v -> {
                    Intent intent = new Intent(getContext(), PickBrushActivity.class);
                    getContext().startActivity(intent);
                }
        );
        designButtons.findViewById(R.id.button_programming).setOnClickListener(
                v -> {
                    BoardManager.addBoard("default", ((EditorMap) MapManager.getMap()).getMapAsBoard()); //todo
                    saveAndProceed(v.getContext(), ScreenEnum.EDITOR_SCREEN);
                });
        designButtons.findViewById(R.id.button_test).setOnClickListener(v -> {
            BoardManager.addBoard("default", ((EditorMap) MapManager.getMap()).getMapAsBoard()); //todo
            saveAndProceed(v.getContext(), ScreenEnum.GAME_SCREEN);
        });
    }

    private void initProgrammingButtons(View designButtons) {
        designButtons.findViewById(R.id.button_design).setOnClickListener(v -> saveAndProceed(v.getContext(), ScreenEnum.DESIGN_SCREEN));
        designButtons.findViewById(R.id.button_test).setOnClickListener(v -> saveAndProceed(v.getContext(), ScreenEnum.GAME_SCREEN));
    }

    private void initDebugButtons(View debugButtons) {
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
    }

    private void saveAndProceed(Context c, ScreenEnum screen) {
        DataManager.saveWorkspace(c.getFilesDir().getAbsolutePath(), saveName, Workspace.getWorkspaceItemsToSave());
        EventBus.getDefault().post(screen);
    }

    private void setToolsLayerContent(ScreenEnum screen) {
        runOnUiThread(() -> {
            switch (screen) {
                case DESIGN_SCREEN:
                    setEditorScreen();
                    break;
                case EDITOR_SCREEN:
                    setProgrammingScreen();
                    //setDebugTools();
                    break;
                case GAME_SCREEN:
                    setGameScreen();
                    break;
            }
        });
    }

    private void setEditorScreen() {
        setLayerState(slidingLayer, true);
        //setLayerState((SlidingLayer) findViewById(R.id.debug_layer), false);
        drawerList.setVisibility(View.GONE);
        LinearLayout editorTools = (LinearLayout) findViewById(R.id.tools_panel);
        inflateTools(editorTools, R.layout.toolset_design);
        initDesignButtons(editorTools);
        resizeSlidingLayer();
    }

    private void setProgrammingScreen() {
        setLayerState(slidingLayer, true);
        drawerList.setVisibility(View.VISIBLE);
        LinearLayout editorTools = (LinearLayout) findViewById(R.id.tools_panel);
        inflateTools(editorTools, R.layout.toolset_programming);
        initProgrammingButtons(editorTools);
        resizeSlidingLayer();
    }

    private void setDebugTools() {
        setLayerState((SlidingLayer) findViewById(R.id.debug_layer), true);
        SlidingLayer debugLayer = (SlidingLayer) findViewById(R.id.debug_layer);
        inflateTools(debugLayer, R.layout.toolset_debug);
        initDebugButtons(debugLayer);
    }

    private void resizeSlidingLayer() {
        ViewGroup.LayoutParams params = slidingLayer.getLayoutParams();
        params.width = findViewById(R.id.tools_panel).getWidth();
        if (drawerList.getVisibility() == View.VISIBLE)
            params.width += drawerList.getWidth();
        slidingLayer.setLayoutParams(params);
    }

    private void setGameScreen() {
        setLayerState(slidingLayer, false);
    }

    private void setLayerState(SlidingLayer layer, boolean enabled) {
        if (enabled)
            layer.openLayer(true);
        else
            layer.closeLayer(true);
        layer.setSlidingEnabled(enabled);
    }

    private void inflateTools(ViewGroup toolsLayer, int resId) {
        toolsLayer.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(resId, toolsLayer);
    }

    @Override
    public void onBackPressed() {
        if (ScreenManager.currentScreen == ScreenEnum.DESIGN_SCREEN) {
            finish();
        } else
            EventBus.getDefault().post(ScreenEnum.DESIGN_SCREEN);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ScreenManager.currentScreen == null)
            EventBus.getDefault().post(ScreenEnum.DESIGN_SCREEN);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                if (ScreenManager.currentScreen != ScreenEnum.GAME_SCREEN)
                    slidingLayer.openLayer(true);
                return true;
        }
        return super.onKeyDown(keycode, e);
    }
}
