package com.fancytank.gamegen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fancytank.gamegen.data.DataManager;
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
        final Context context = convertView.getContext();
        title.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSave(name);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getDeleteProjectDialog(context, name);
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

    private void getDeleteProjectDialog(final Context context, final String saveName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("do you want to delete " + saveName);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataManager.deleteProject(context.getFilesDir().getAbsolutePath(), saveName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
