package com.fancytank.gamegen.editor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fancytank.gamegen.R;

import java.io.IOException;
import java.io.InputStream;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    AssetManager assetManager;

    public ImageAdapter(Context c) {
        context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assetManager = context.getAssets();
    }

    public int getCount() {
        return textureNames.length;
    }

    public Object getItem(int position) {
        return textureNames[position];
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
        } else {
            imageView = convertView;
        }

        ((ImageView) imageView.findViewById(R.id.image)).setImageDrawable(getImage(context, textureNames[position]));
        return imageView;
    }

    private Drawable getImage(Context c, String name) {
        System.out.println(c.getFilesDir().getAbsolutePath() + name);
        try {
            InputStream is = assetManager.open(name + ".png");
            Drawable output = Drawable.createFromStream(is, null);
            is.close();
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}