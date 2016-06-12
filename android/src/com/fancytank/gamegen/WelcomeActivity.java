package com.fancytank.gamegen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.fancytank.gamegen.data.DataManager;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private SaveListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<String> saves = DataManager.getFileNames(this.getFilesDir().getAbsolutePath());
        adapter = new SaveListAdapter(this, saves);

        ListView projectList = (ListView) findViewById(R.id.saved_projects_list);
        projectList.setAdapter(adapter);

        View footerView = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.main_footer, null, false);
        projectList.addFooterView(footerView);
    }

    public void goToProgramming(View view) {
        ProjectDialog.geNewProjectDialog(view.getContext());
    }

}
