package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fancytank.gamegen.R;

public class PickBrushActivity extends Activity {
    GridView gridView;
    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);
        gridView = (GridView) findViewById(R.id.tile_texture);
        gridView.setAdapter(new TileTypeAdapter(this));
        gridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> selectedId = position);
    }

    public void confirmBrush(View view) {
        String selectedTile = (String) gridView.getAdapter().getItem(selectedId);
        EditorActor.setBrush(selectedTile);
        super.onBackPressed();
    }
}
