package com.fancytank.gamegen.programming;

public enum Direction {
    UP(0, 1),
    LEFT(-1, 0),
    DOWN(0, -1),
    RIGHT(1, 0);

    public final int deltaX, deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Direction flip() {
        return values()[(ordinal() + 2) % 4];
    }
}