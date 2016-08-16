package com.fancytank.gamegen;

import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.MapManager;

public class DesignScreen extends AbstractScreen {
    @Override
    public void buildStage() {
        new ActorInitializer();
        new MapManager(getWidth(), getHeight(), new EditorMap());
    }
}
