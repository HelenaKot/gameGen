package com.fancytank.gamegen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.programming.ProgrammingActivity;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        List<String> saves = DataManager.getFileNames(this.getFilesDir().getAbsolutePath());
        SaveListAdapter adapter = new SaveListAdapter(this, saves);

        ListView projectList = (ListView) findViewById(R.id.saved_projects_list);
        projectList.setAdapter(adapter);

        View footerView = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.main_footer, null, false);
        projectList.addFooterView(footerView);
    }

    public void goToProgramming(View view) {
        getAlertDialog();
    }

    private void getAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("enter new project Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newProject(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void newProject(String fineName) {
        Intent intent = new Intent(this, ProgrammingActivity.class);
        intent.putExtra("saveName", fineName);
        startActivity(intent);
    }

}
