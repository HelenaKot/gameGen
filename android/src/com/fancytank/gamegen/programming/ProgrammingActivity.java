package com.fancytank.gamegen.programming;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.R;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        contentFrame.addView(initializeForView(new MainGdx()));
        slidingLayer = (SlidingLayer) findViewById(R.id.sliding_layer);
        list = new BlocksExpendableList((ExpandableListView) findViewById(R.id.drawer_list), this);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(MainGdx.AppStatus status) {
        if (status == MainGdx.AppStatus.GDX_INIT_FINISHED)
            EventBus.getDefault().post(MainGdx.AppStatus.EDITOR_SCREEN);
        else if (status == MainGdx.AppStatus.SETUP_FINISHED)
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

    public static String myFilename = "todotodo";

    public void saveWorkspace(View view) throws IOException {
        File file = new File(view.getContext().getFilesDir(), myFilename);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(Workspace.getWorkspaceItemsToSave());
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void loadWorkspace(View view) throws IOException, ClassNotFoundException {
        File file = new File(view.getContext().getFilesDir(), myFilename);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ProgrammingBlockSavedInstance[] data = (ProgrammingBlockSavedInstance[]) objectInputStream.readObject();
            Workspace.load(data);
        }
    }

    public void deleteAll(View view) {
        Workspace.clearWorkspace();
    }
}
