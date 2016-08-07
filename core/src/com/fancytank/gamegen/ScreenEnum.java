package com.fancytank.gamegen;

public enum ScreenEnum {
    EDITOR_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return setInstance(new EditorScreen());
        }
    },
    GAME_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return setInstance(new GameScreen());
        }
    };

    static public AbstractScreen screenInstance;

    private static AbstractScreen setInstance(AbstractScreen screen) {
        if (screenInstance != null)
            screenInstance.dispose();
        screenInstance = screen;
        return screenInstance;
    }

    public abstract AbstractScreen getScreen();
}
