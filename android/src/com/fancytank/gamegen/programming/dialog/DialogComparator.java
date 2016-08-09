package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.RadioGroup;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.data.ValueType;

import static com.fancytank.gamegen.programming.dialog.DialogSpawner.initDialog;

public class DialogComparator {
    private static String[] values = {"<", "=", ">"};

    static void compareDialog(final Context context, final BlockActorPattern pattern) {
        final BuilderWrapper dialog = initDialog(context, "pick equation", R.layout.dialog_compare);
        final RadioGroup radioGroup = (RadioGroup) dialog.view.findViewById(R.id.compare_radio_group);
        radioGroup.getChildAt(0).setSelected(true);
        dialog.builder.setPositiveButton("OK", (DialogInterface d, int which) -> {
            int myCase;
            switch (myCase = radioGroup.indexOfChild(dialog.view.findViewById(radioGroup.getCheckedRadioButtonId()))) {
                case 0:
                case 1:
                case 2:
                    pattern.setValue(values[myCase], ValueType.ANY);
                    pattern.spawn();
                default:
                    break;
            }
        });
        dialog.builder.show();
    }


}
