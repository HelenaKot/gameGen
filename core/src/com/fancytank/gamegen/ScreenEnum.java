package com.fancytank.gamegen;

public enum ScreenEnum {
    EDITOR_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            screenInstance = new EditorScreen();
            return screenInstance;
        }
    },
    GAME_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            screenInstance = new GameScreen();
            return screenInstance;
        }
    };

    static public AbstractScreen screenInstance;

    public abstract AbstractScreen getScreen();
}
