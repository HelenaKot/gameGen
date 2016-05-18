package com.fancytank.gamegen.programming.blocks;

public class BlockCreateEvent {
    public BlockActorPattern blockActorPattern;

    public BlockCreateEvent(BlockActorPattern blockActorPattern) {
        this.blockActorPattern = blockActorPattern;
    }
}
