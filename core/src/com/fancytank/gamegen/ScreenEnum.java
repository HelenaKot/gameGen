package com.fancytank.gamegen;

public enum ScreenEnum {
    EDITOR_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            screenType = this;
            return  new EditorScreen();
        }
    },
    GAME_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            screenType = this;
            return new GameScreen();
        }
    };

    static public ScreenEnum screenType;
    static public AbstractScreen screenInstance;

    public abstract AbstractScreen getScreen();
}
