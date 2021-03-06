package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.editor.MyColors;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;

import uz.shift.colorpicker.LineColorPicker;

public enum DialogSpawner {
    DIALOG_NUMBER, DIALOG_COLOR, DIALOG_SCREEN, DIALOG_LOOP, DIALOG_COMPARATOR, DIALOG_INIT_VAR, DIALOG_GET_VAR, DIALOG_SET_VAR, DIALOG_PICK_CLASS;

    public void getDialog(final Context context, BlockActorPattern pattern) {
        switch (this) {
            case DIALOG_NUMBER:
                enterNumberDialog(context, pattern).builder.show();
                break;
            case DIALOG_COLOR:
                colorPickerDialog(context, pattern);
                break;
            case DIALOG_SCREEN:
                DialogVariables.screenPickerDialog(context, pattern);
                break;
            case DIALOG_LOOP:
                DialogLoop.newLoopDialog(context);
                break;
            case DIALOG_COMPARATOR:
                DialogComparator.compareDialog(context, pattern);
                break;
            case DIALOG_INIT_VAR:
                DialogVariables.newVariableDialog(context, pattern);
                break;
            case DIALOG_GET_VAR:
                DialogVariables.getVariableDialog(context, pattern);
                break;
            case DIALOG_SET_VAR:
                DialogVariables.setVariableDialog(context, pattern);
                break;
            case DIALOG_PICK_CLASS:
                DialogVariables.varClassName(context, pattern);
                break;
        }
    }

    public static BuilderWrapper enterNumberDialog(final Context context, final BlockActorPattern pattern) {
        final EditText input = new EditText(context);
        input.setHint("0");
        input.setRawInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        BuilderWrapper dialog = initDialog(context, "enter value", input);

        dialog.builder.setPositiveButton("OK", (d, which) -> {
            String value = input.getText().toString();
            if (value.equals("")) value = "0";
            pattern.setValue(value, ValueType.NUMBER);
            pattern.spawn();
        });
        return dialog;
    }

    public static void colorPickerDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "pick color", R.layout.dialog_color_picker);
        final LineColorPicker lineColorPicker = (LineColorPicker) dialog.view.findViewById(R.id.picker);
        lineColorPicker.setColors(MyColors.getPalette());
        dialog.builder.setPositiveButton("OK", (d, which) -> {
            String value = String.format("#%06x", (0xFFFFFF & lineColorPicker.getColor()));
            pattern.setValue(value, ValueType.COLOR);
            pattern.spawn();
        });
        dialog.builder.show();
    }

    public static void setLoopPatterns(BlockActorPattern[] patterns) {
        DialogLoop.loopPatterns = patterns;
    }

    static BuilderWrapper initDialog(Context context, String title, @LayoutRes int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        return initDialog(context, title, view);
    }

    static BuilderWrapper initDialog(Context context, String title, View view) {
        BuilderWrapper output = new BuilderWrapper();
        output.builder = new AlertDialog.Builder(context);
        output.view = view;
        output.builder.setView(output.view);
        output.builder.setTitle(title);

        output.builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return output;
    }
}
