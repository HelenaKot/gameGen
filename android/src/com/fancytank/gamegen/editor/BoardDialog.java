package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.BoardManager;

import org.greenrobot.eventbus.EventBus;

public class BoardDialog {

    public static void geNewBoardDialog(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("enter new board name");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (DialogInterface dialog, int which) -> {
            String saveName = input.getText().toString();
            if (saveName.length() > 0) {
                Board newBoard = new Board(new TileType("empty", null, "#000000"));
                BoardManager.addBoard(saveName, newBoard);
                EventBus.getDefault().post(saveName);
                context.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", (DialogInterface dialog, int which) -> dialog.cancel());
        builder.show();
    }
}
