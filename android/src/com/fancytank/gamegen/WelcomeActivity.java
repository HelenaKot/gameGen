package com.fancytank.gamegen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void goToProgramming(View view) {
        Intent intent = new Intent(this, ProgrammingActivity.class);
        startActivity(intent);
    }

    public void goToDesign(View view) {
        Intent intent = new Intent(this, DesignActivity.class);
        startActivity(intent);
    }
}
