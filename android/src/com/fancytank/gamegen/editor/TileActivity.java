package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;

public class TileActivity extends Activity {
    EditText tileName;
    GridView gridView;
    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        tileName = (EditText) findViewById(R.id.tile_name);
        gridView = (GridView) findViewById(R.id.tile_texture);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> selectedId = position);
    }

    public void createTileClass(View view) {
        String name = tileName.getText().toString();
        String imageName = (String) gridView.getAdapter().getItem(selectedId);
        if (name.length() > 0)
            ActorInitializer.addActorClass(name, imageName, Color.WHITE.toString());
        super.onBackPressed();
    }
}
