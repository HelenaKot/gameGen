package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fancytank.gamegen.R;

public class PickBrushActivity extends Activity {
    GridView gridView;
    static final int BRUSH_REQUEST_CODE = 1337;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_brush);
        gridView = (GridView) findViewById(R.id.tile_texture);
        gridView.setAdapter(new TileTypeAdapter(this));
        gridView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) ->
                onItemClick(position)
        );
    }

    private void confirmBrush(String className) {
        EditorActor.setBrush(className);
        super.onBackPressed();
    }

    private void onItemClick(int id) {
        if (id < gridView.getCount() - 1)
            confirmBrush((String) gridView.getAdapter().getItem(id));
        else
            onFooterClick();
    }

    private void onFooterClick() {
        Intent intent = new Intent(context, TileActivity.class);
        context.startActivityForResult(intent, BRUSH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BRUSH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                confirmBrush(data.getExtras().getString(TileActivity.NAME_KEY));
            }
        }
    }
}
