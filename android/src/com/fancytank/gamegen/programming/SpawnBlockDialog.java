package com.fancytank.gamegen.programming;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.Palette;

public enum SpawnBlockDialog {
    DIALOG_NUMBER, DIALOG_COLOR, DIALOG_LOOP, DIALOG_INIT_VAR, DIALOG_GET_VAR, DIALOG_SET_VAR;

    public void getDialog(final Context context, BlockActorPattern pattern) {
        switch (this) {
            case DIALOG_NUMBER:
                enterNumberDialog(context, pattern);
                break;
            case DIALOG_COLOR:
                colorPickerDialog(context, pattern);
                break;
            case DIALOG_LOOP:
                loopDialog(context);
                break;
            case DIALOG_INIT_VAR:
                newVariableDialog(context, pattern);
                break;
            case DIALOG_GET_VAR:
                getVariableDialog(context, pattern);
                break;
            case DIALOG_SET_VAR:
                setVariableDialog(context, pattern);
                break;
        }
    }

    public static void enterNumberDialog(final Context context, final BlockActorPattern pattern) {
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        BuilderWrapper dialog = initDialog(context, "enter value", input);

        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                pattern.setValue(value, ValueType.NUMBER);
                pattern.spawn();
            }
        });
        dialog.builder.show();
    }

    public static void colorPickerDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "pick color", R.layout.dialog_color_picker);
        final LineColorPicker lineColorPicker = (LineColorPicker) dialog.view.findViewById(R.id.picker);
        lineColorPicker.setColors(Palette.DEFAULT);
        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = String.format("#%06X", (0xFFFFFF & lineColorPicker.getColor()));
                pattern.setValue(value, ValueType.COLOR);
                pattern.spawn();
            }
        });
        dialog.builder.show();
    }

    public static void newVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "initialize a variable", R.layout.dialog_new_var);
        final Spinner varSpinner = (Spinner) dialog.view.findViewById(R.id.spinner);
        final EditText editText = (EditText) dialog.view.findViewById(R.id.editText);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, ValueType.getValueStrings());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varSpinner.setAdapter(dataAdapter);
        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ValueType selectedType = ValueType.getValuesList()[varSpinner.getSelectedItemPosition()];
                String value = editText.getText().toString();
                VariableList.put(value, "0", selectedType);
                pattern.setValue(value, selectedType);
                pattern.setLabel(value + " (" + selectedType.toString().toLowerCase() + ")");
                pattern.spawn();
            }
        });
        dialog.builder.show();
    }

    public static void getVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "get variable", R.layout.dialog_get_var);
        final Spinner varSpinner = getVarSpinner(context, dialog);
        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.VARIABLE);
                pattern.setValue(selectedVariable);
                pattern.spawn();
            }
        });
        dialog.builder.show();
    }

    private static void setVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "set variable", R.layout.dialog_get_var);
        final Spinner varSpinner = getVarSpinner(context, dialog);
        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.VARIABLE);
                pattern.setValue(selectedVariable);
                pattern.setLabel(selectedVariable.getValue() + " =");
                pattern.spawn();
            }
        });
        dialog.builder.show();
    }

    private static Spinner getVarSpinner(final Context context, BuilderWrapper dialog) {
        Spinner varSpinner = (Spinner) dialog.view.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, VariableList.getKeys());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varSpinner.setAdapter(dataAdapter);
        return varSpinner;
    }

    public static void loopDialog(final Context context) {
        BuilderWrapper dialog = initDialog(context, "loop type", R.layout.dialog_loop_spawner);
        final LoopDialog radioSet = new LoopDialog(dialog);
        dialog.builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoopDialog.loopPatterns[radioSet.getChecked()].spawn();
            }
        });
        dialog.builder.show();
    }

    public static void setLoopPatterns(BlockActorPattern[] patterns) {
        LoopDialog.loopPatterns = patterns;
    }

    private static BuilderWrapper initDialog(Context context, String title, @LayoutRes int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        return initDialog(context, title, view);
    }

    private static BuilderWrapper initDialog(Context context, String title, View view) {
        BuilderWrapper output = new BuilderWrapper();
        output.builder = new AlertDialog.Builder(context);
        output.view = view;
        output.builder.setView(output.view);
        output.builder.setTitle(title);

        output.builder.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return output;
    }

    static class BuilderWrapper {
        AlertDialog.Builder builder;
        View view;
    }
}
