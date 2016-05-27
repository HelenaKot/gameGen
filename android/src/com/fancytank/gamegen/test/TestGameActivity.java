package com.fancytank.gamegen.test;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fancytank.gamegen.GameScreen;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.ProgrammingActivity;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestGameActivity extends AndroidApplication {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_game);
        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new MainGdx());
        contentFrame.addView(gdxView);
        view = contentFrame;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(MainGdx.AppStatus status) {
        if (status == MainGdx.AppStatus.GDX_INIT_FINISHED)
            EventBus.getDefault().post(MainGdx.AppStatus.TEST_SCREEN);
        if (status == MainGdx.AppStatus.SETUP_FINISHED)
            try {
                GameScreen.loadGame(loadDataFromFile());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    //todo to refactor + ProgrammingActivity
    private ProgrammingBlockSavedInstance[] loadDataFromFile() throws IOException, ClassNotFoundException {
        File file = new File(view.getContext().getFilesDir(), ProgrammingActivity.myFilename);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ProgrammingBlockSavedInstance[] data = (ProgrammingBlockSavedInstance[]) objectInputStream.readObject();
            return data;
        }
        return null;
    }
}
