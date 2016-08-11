package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;

public class TileActivity extends Activity {
    EditText tileName;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        tileName = (EditText) findViewById(R.id.tile_name);
        gridView = (GridView) findViewById(R.id.tile_texture);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Toast.makeText(this, "" + position,
                    Toast.LENGTH_SHORT).show();
        });
    }

    public void createTileClass(View view) {
    }
}
