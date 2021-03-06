package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;

import uz.shift.colorpicker.LineColorPicker;

public class TileActivity extends Activity {
    static String NAME_KEY = "name";
    private EditText tileName;
    private GridView gridView;
    private LineColorPicker lineColorPicker;
    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        tileName = (EditText) findViewById(R.id.tile_name);
        gridView = (GridView) findViewById(R.id.tile_texture);
        lineColorPicker = (LineColorPicker) findViewById(R.id.picker);
        lineColorPicker.setColors(MyColors.getPalette());
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> selectedId = position);
    }

    public void createTileClass(View view) {
        String name = tileName.getText().toString();
        String imageName = (String) gridView.getAdapter().getItem(selectedId);
        if (name.length() > 0) {
            ActorInitializer.addActorClass(name, imageName, "#" + Integer.toHexString(lineColorPicker.getColor()).substring(2, 8));
            postResult(name);
        } else
            setResult(RESULT_CANCELED);
        finish();
    }

    private void postResult(String name) {
        Intent result = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(NAME_KEY, name);
        result.putExtras(bundle);
        setResult(RESULT_OK, result);
    }

}
