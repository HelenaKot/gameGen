package com.fancytank.gamegen.programming;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.fancytank.gamegen.R;

public class LoopDialog {
    private RadioButton[] buttons;
    private EditText editText;
    SpawnBlockDialog.BuilderWrapper dialog;

    LoopDialog(SpawnBlockDialog.BuilderWrapper dialog) {
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

    public int getNumber() {
        return Integer.parseInt(editText.getText().toString());
    }

    private void setupDialog(View view) {
        buttons = setupRadioButtons(view);
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
            }
        });
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
