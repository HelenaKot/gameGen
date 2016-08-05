package com.fancytank.gamegen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fancytank.gamegen.programming.ProgrammingActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class SaveListAdapter extends ArrayAdapter {
    public ArrayList<File> saves;
    public static SaveListAdapter instance;

    public SaveListAdapter(Context context, ArrayList<File> objects) {
        super(context, R.layout.main_save_row, objects);
        saves = objects;
        instance = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final File save = saves.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_save_row, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title),
                subtitle = (TextView) convertView.findViewById(R.id.subtitle);
        final Context context = convertView.getContext();
        title.setText(save.getName());
        subtitle.setText(new Date(save.lastModified()).toString());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSave(save.getName());
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ProjectDialog.getDeleteProjectDialog(context, save.getName(), position);
                return true;
            }
        });

        return convertView;
    }

    private void setSave(String saveName) {
        Intent intent = new Intent(getContext(), ProgrammingActivity.class);
        intent.putExtra("saveName", saveName);
        getContext().startActivity(intent);
    }

}
