package com.fancytank.gamegen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.programming.ProgrammingActivity;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private String fileNameToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<String> saves = DataManager.getFileNames(this.getFilesDir().getAbsolutePath());
        SaveListAdapter adapter = new SaveListAdapter(this, saves);

        ListView projectList = (ListView) findViewById(R.id.saved_projects_list);
        projectList.setAdapter(adapter);
    }

    public void goToProgramming(View view) {
        Intent intent = new Intent(this, ProgrammingActivity.class);
        startActivity(intent);
    }
}
