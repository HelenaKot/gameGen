package com.fancytank.gamegen;

public enum ScreenEnum {
    DESIGN_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return setInstance(new DesignScreen(), this);
        }
    },
    EDITOR_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return setInstance(new EditorScreen(), this);
        }
    },
    GAME_SCREEN {
        @Override
        public AbstractScreen getScreen() {
            return setInstance(new GameScreen(), this);
        }
    };

    static public AbstractScreen screenInstance;
    public static ScreenEnum currentScreen = ScreenEnum.DESIGN_SCREEN;

    private static AbstractScreen setInstance(AbstractScreen screen, ScreenEnum id) {
        currentScreen = id;
        if (screenInstance != null)
            screenInstance.dispose();
        screenInstance = screen;
        return screenInstance;
    }

    public abstract AbstractScreen getScreen();
}
