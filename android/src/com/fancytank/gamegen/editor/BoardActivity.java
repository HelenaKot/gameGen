package com.fancytank.gamegen.editor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.BoardManager;

import java.util.LinkedList;

public class BoardActivity extends Activity {
    ListView boardList;
    ArrayAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardList = (ListView) findViewById(R.id.board_list);
        boardList.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Hello"); //todo
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetAdapter();
    }

    private void resetAdapter() {
        LinkedList<Board> boards = new LinkedList<>(BoardManager.getBoards().values());
        boardAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, boards);
        boardList.setAdapter(boardAdapter);
    }
}
