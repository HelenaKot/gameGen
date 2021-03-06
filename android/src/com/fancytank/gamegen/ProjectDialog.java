package com.fancytank.gamegen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import com.fancytank.gamegen.data.DataManager;
import com.fancytank.gamegen.game.map.BoardManager;

public class ProjectDialog {

    public static void geNewProjectDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("enter new project Name");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (DialogInterface dialog, int which) -> {
            String saveName = input.getText().toString();
            new BoardManager();//todo
            newProject(context, saveName);
        });
        builder.setNegativeButton("Cancel", (DialogInterface dialog, int which) -> dialog.cancel());
        builder.show();
    }

    private static void newProject(Context context, String fineName) {
        Intent intent = new Intent(context, GDXActivity.class);
        intent.putExtra("saveName", fineName);
        context.startActivity(intent);
    }

    public static void getDeleteProjectDialog(final Context context, final String saveName, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("do you want to delete " + saveName);

        builder.setPositiveButton("OK", (DialogInterface dialog, int which) -> {
            SaveListAdapter.instance.saves.remove(index);
            DataManager.deleteProject(saveName);
        });
        builder.setNegativeButton("Cancel", (DialogInterface dialog, int which) -> dialog.cancel());
        builder.show();
    }
}
