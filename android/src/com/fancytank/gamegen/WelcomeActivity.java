package com.fancytank.gamegen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.fancytank.gamegen.data.DataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity {
    private static WelcomeActivity instance;
    private SaveListAdapter adapter;
    private ListView projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_welcome);
        projectList = (ListView) findViewById(R.id.saved_projects_list);
        View footerView = ((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.main_footer, null, false);
        projectList.addFooterView(footerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetAdapter();
    }

    public void goToProgramming(View view) {
        ProjectDialog.geNewProjectDialog(view.getContext());
    }

    public static void resetAdapter() {
        ArrayList<File> files = new ArrayList<>(Arrays.asList(DataManager.getFiles(instance.getFilesDir().getAbsolutePath())));
        instance.adapter = new SaveListAdapter(instance, files);
        instance.projectList.setAdapter(instance.adapter);
    }

}
