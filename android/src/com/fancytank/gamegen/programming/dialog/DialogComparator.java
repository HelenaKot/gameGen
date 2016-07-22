package com.fancytank.gamegen.programming.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
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
        dialog.builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            View context = dialog.view;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int myCase;
                switch (myCase = radioGroup.indexOfChild(context.findViewById(radioGroup.getCheckedRadioButtonId()))) {
                    case 0:
                    case 1:
                    case 2:
                        pattern.setValue(values[myCase], ValueType.ANY);
                        pattern.spawn();
                    default:
                        break;
                }

            }
        });
        dialog.builder.show();
    }


}
