package com.fancytank.gamegen;

public enum ScreenEnum {
    EDITOR_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return saveInstance(new EditorScreen());
        }
    },
    GAME_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return saveInstance(new GameScreen());
        }
    };

    static public AbstractScreen screenInstance;

    private static AbstractScreen saveInstance(AbstractScreen screen) {
        if (screenInstance != null)
            screenInstance.dispose();
        screenInstance = screen;
        return screenInstance;
    }

    public abstract AbstractScreen getScreen();
}
