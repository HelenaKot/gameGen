package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.map.BoardManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class BoardActivity extends Activity {
    ListView boardList;
    ArrayAdapter boardAdapter;
    private ArrayList<String> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardList = (ListView) findViewById(R.id.board_list);
        boardList.setOnItemClickListener((parent, view, position, id) -> {
            EventBus.getDefault().post(boards.get((int) id));
            onBackPressed();
        });
        View footerView = ((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_board, null, false);
        boardList.addFooterView(footerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetAdapter();
    }

    private void resetAdapter() {
        boards = new ArrayList<>(BoardManager.getBoards().keySet());
        boardAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, boards);
        boardList.setAdapter(boardAdapter);
    }

    public void spawnBoard(View view) {
        BoardDialog.geNewBoardDialog(this);
    }
}