package com.fancytank.gamegen;

import com.fancytank.gamegen.game.actor.ActorInitializer;

public class DesignScreen extends AbstractScreen {
    @Override
    public void buildStage() {
        new ActorInitializer();
    }
}
