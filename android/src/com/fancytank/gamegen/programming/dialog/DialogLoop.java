package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;

import static com.fancytank.gamegen.programming.dialog.DialogSpawner.initDialog;

public class DialogLoop {
    static BlockActorPattern[] loopPatterns;
    private RadioButton[] buttons;
    private EditText editText;
    DialogSpawner.BuilderWrapper dialog;

    static void newLoopDialog(final Context context) {
        DialogSpawner.BuilderWrapper dialog = DialogSpawner.initDialog(context, "loop type", R.layout.dialog_loop_spawner);
        final DialogLoop radioSet = new DialogLoop(dialog);
        dialog.builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogLoop.loopPatterns[radioSet.getChecked()].spawn();
            }
        });
        dialog.builder.show();
    }

    DialogLoop(DialogSpawner.BuilderWrapper dialog) {
        editText = (EditText) dialog.view.findViewById(R.id.times_text);
        this.dialog = dialog;
        setupDialog(dialog.view);
    }

    public int getChecked() {
        for (int i = 0; i < buttons.length; i++)
            if (buttons[i].isChecked())
                return i;
        return -1;
    }

    private void setupDialog(View view) {
        buttons = setupRadioButtons(view);
        setLoopTimes("4");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttons[2].setChecked(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoopTimes(s.toString());
            }
        });
    }

    private void setLoopTimes(String times) {
        loopPatterns[2].setValue(times, ValueType.INT_NUMBER);
        loopPatterns[2].setLabel("Loop " + times + " times");
    }

    private RadioButton[] setupRadioButtons(View view) {
        RadioButton[] buttons = {(RadioButton) view.findViewById(R.id.radio_while),
                (RadioButton) view.findViewById(R.id.radio_list),
                (RadioButton) view.findViewById(R.id.radio_times),
        };

        for (int i = 0; i < buttons.length; i++) {
            final int idCopy = i;
            buttons[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) emulateRadioSet(idCopy);
                }
            });
        }
        return buttons;
    }

    private void emulateRadioSet(int buttonNo) {
        for (int i = 0; i < buttons.length; i++)
            if (i != buttonNo)
                buttons[i].setChecked(false);
    }
}
