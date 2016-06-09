package com.fancytank.gamegen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fancytank.gamegen.programming.ProgrammingActivity;

import java.util.List;

public class SaveListAdapter extends ArrayAdapter<String> {
    private List<String> saveNames;

    public SaveListAdapter(Context context, List<String> objects) {
        super(context, R.layout.main_save_row, objects);
        saveNames = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String name = saveNames.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_save_row, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title),
                subtitle = (TextView) convertView.findViewById(R.id.subtitle);

        title.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSave(name);
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
