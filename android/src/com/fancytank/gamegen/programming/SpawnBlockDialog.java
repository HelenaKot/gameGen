package com.fancytank.gamegen.programming;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.Palette;

public enum SpawnBlockDialog {
    DIALOG_NUMBER, DIALOG_COLOR;

    public void getDialog(final Context context, BlockActorPattern pattern) {
        switch (this) {
            case DIALOG_NUMBER:
                enterNumberDialog(context, pattern);
                break;
            case DIALOG_COLOR:
                colorPickerDialog(context, pattern);
                break;
        }
    }

    public static void enterNumberDialog(final Context context, final BlockActorPattern pattern) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("enter value");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                pattern.setValue(value, ValueType.NUMBER);
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

    public static void colorPickerDialog(final Context context, final BlockActorPattern pattern) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("pick color");

        LayoutInflater inflater = LayoutInflater.from(context);
        View pickerView = inflater.inflate(R.layout.line_color_picker, null);
        final LineColorPicker lineColorPicker = (LineColorPicker) pickerView.findViewById(R.id.picker);
        lineColorPicker.setColors(Palette.DEFAULT);
        builder.setView(pickerView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = String.format("#%06X", (0xFFFFFF & lineColorPicker.getColor()));
                pattern.setValue(value, ValueType.COLOR);
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
