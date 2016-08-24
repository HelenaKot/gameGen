package com.fancytank.gamegen.editor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.TileType;

import java.io.IOException;
import java.io.InputStream;

public class TileTypeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    AssetManager assetManager;
    private String[] actorNames = ActorInitializer.getActorNames();

    public TileTypeAdapter(Context c) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assetManager = c.getAssets();
    }

    @Override
    public int getCount() {
        return actorNames.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return actorNames[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null && position < actorNames.length)
            return getView(position);
        else if (convertView == null)
            return getFooter();
        else
            return convertView;
    }

    private View getView(int position) {
        View imageView = inflater.inflate(R.layout.image_row, null);
        imageView.setLayoutParams(new GridView.LayoutParams(248, 320));
        imageView.setPadding(8, 8, 8, 8);
        if (ActorInitializer.getActorTile(actorNames[position]).textureName != null)
            ((ImageView) imageView.findViewById(R.id.image)).setImageDrawable(getImage(actorNames[position]));
        ((TextView) imageView.findViewById(R.id.text)).setText(actorNames[position]);
        return imageView;
    }

    private View getFooter() {
        View footerView = inflater.inflate(R.layout.footer_brush, null);
        footerView.setLayoutParams(new GridView.LayoutParams(248, 320));
        footerView.setPadding(8, 8, 8, 8);
        return footerView;
    }

    private Drawable getImage(String name) {
        TileType tile = ActorInitializer.getActorTile(name);
        try {
            InputStream is = assetManager.open(tile.textureName + ".png");
            Drawable output = Drawable.createFromStream(is, null);
            output.setColorFilter(Color.parseColor(tile.colorHex), PorterDuff.Mode.MULTIPLY);
            is.close();
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}