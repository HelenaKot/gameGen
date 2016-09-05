package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.dialog.DialogSpawner.initDialog;

public class DialogVariables {
    static void newVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "initialize a variable", R.layout.dialog_new_var);
        final Spinner varSpinner = (Spinner) dialog.view.findViewById(R.id.spinner);
        final EditText editText = (EditText) dialog.view.findViewById(R.id.editText);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, ValueType.getValueStrings());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varSpinner.setAdapter(dataAdapter);
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
            ValueType selectedType = ValueType.getValuesList()[varSpinner.getSelectedItemPosition()];
            String value = editText.getText().toString();
            VariableList.put(value, "0", selectedType);
            pattern.setValue(value, selectedType);
            pattern.setExpectedValue(0, selectedType);
            pattern.setLabel(value + " (" + selectedType.toString().toLowerCase() + ")");
            pattern.spawn();
        });
        dialog.builder.show();
    }

    static void getVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "get variable", R.layout.dialog_get_var);
        final Spinner varSpinner = setupSpinner(context, dialog, "get", VariableList.getKeys());
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
                    Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.VARIABLE);
                    pattern.setValue(selectedVariable);
                    pattern.setLabel(varSpinner.getSelectedItem().toString());
                    pattern.spawn();
                }
        );
        dialog.builder.show();
    }

    static void setVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "set variable", R.layout.dialog_get_var);
        final Spinner varSpinner = setupSpinner(context, dialog, "set", VariableList.getKeys());
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
            Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.VARIABLE);
            pattern.setValue(selectedVariable);
            pattern.setExpectedValue(0, VariableList.get(selectedVariable.getDirectValue()).valueType);
            pattern.setLabel(varSpinner.getSelectedItem() + " =");
            pattern.spawn();

        });
        dialog.builder.show();
    }

    public static void screenPickerDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "pick screen", R.layout.dialog_get_var);
        final Spinner varSpinner = setupSpinner(context, dialog, "screen", BoardManager.getBoards().keySet().toArray(new String[BoardManager.getBoards().size()]));
        dialog.builder.setPositiveButton("OK", (d, which) -> {
            pattern.setValue((String) varSpinner.getSelectedItem(), ValueType.SCREEN);
            pattern.spawn();
        });
        dialog.builder.show();
    }

    static void varClassName(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "tile type", R.layout.dialog_get_var);
        final Spinner varSpinner = setupSpinner(context, dialog, "tile", ActorInitializer.getActorNames());
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
            Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.CLASS_NAME);
            pattern.setValue(selectedVariable);
            pattern.spawn();

        });
        dialog.builder.show();
    }

    private static Spinner setupSpinner(final Context context, BuilderWrapper dialog, String text, String[] args) {
        Spinner varSpinner = (Spinner) dialog.view.findViewById(R.id.spinner);
        TextView textView = (TextView) dialog.view.findViewById(R.id.textView);
        textView.setText(text);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, args);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varSpinner.setAdapter(dataAdapter);
        return varSpinner;
    }
}
