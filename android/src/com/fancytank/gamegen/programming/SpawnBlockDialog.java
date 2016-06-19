package com.fancytank.gamegen.programming;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import com.fancytank.gamegen.programming.blocks.BlockActorPattern;

public enum SpawnBlockDialog {
    DIALOG_NUMBER, DIALOG_COLOR;

    AlertDialog.Builder dialogBuilder;

    public void getDialog(final Context context, BlockActorPattern pattern) {
        switch (this) {
            case DIALOG_NUMBER:
                enterValueDialog(context, pattern);
                break;
            case DIALOG_COLOR:
                break;
        }
    }

    public static void enterValueDialog(final Context context, final BlockActorPattern pattern) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("enter value");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                pattern.setValue(value);
                pattern.spawn();
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
