package com.fancytank.gamegen.editor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;

import java.io.IOException;
import java.io.InputStream;

public class TileTypeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    AssetManager assetManager;
    private String[] actorNames = ActorInitializer.getActorNames();

    public TileTypeAdapter(Context c) {
        context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assetManager = context.getAssets();
    }

    public int getCount() {
        return actorNames.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View imageView;
        if (convertView == null) {
            imageView = inflater.inflate(R.layout.image_row, null);
            imageView.setLayoutParams(new GridView.LayoutParams(248, 248));
            imageView.setPadding(8, 8, 8, 8);
            if (ActorInitializer.getActorTile(actorNames[position]).textureName != null)
                ((ImageView) imageView.findViewById(R.id.image)).setImageDrawable(getImage(context, actorNames[position]));
        } else {
            imageView = convertView;
        }

        return imageView;
    }

    private Drawable getImage(Context c, String name) {
        System.out.println(c.getFilesDir().getAbsolutePath() + name);
        try {
            InputStream is = assetManager.open(ActorInitializer.getActorTile(name).textureName + ".png");
            Drawable output = Drawable.createFromStream(is, null);
            output.setColorFilter(ActorInitializer.getActorTile(name).tint.toIntBits(), PorterDuff.Mode.MULTIPLY);
            is.close();
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}