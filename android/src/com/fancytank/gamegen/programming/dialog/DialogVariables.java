package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;

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
            pattern.setLabel(value + " (" + selectedType.toString().toLowerCase() + ")");
            pattern.spawn();
        });
        dialog.builder.show();
    }

    static void getVariableDialog(final Context context, final BlockActorPattern pattern) {
        BuilderWrapper dialog = initDialog(context, "get variable", R.layout.dialog_get_var);
        final Spinner varSpinner = getVarSpinner(context, dialog);
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
        final Spinner varSpinner = getVarSpinner(context, dialog);
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
            Variable selectedVariable = new Variable((String) varSpinner.getSelectedItem(), ValueType.VARIABLE);
            pattern.setValue(selectedVariable);
            pattern.setLabel(varSpinner.getSelectedItem() + " =");
            pattern.spawn();

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
}
