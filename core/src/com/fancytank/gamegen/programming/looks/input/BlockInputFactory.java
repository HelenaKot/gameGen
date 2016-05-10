package com.fancytank.gamegen.programming.looks.input;

import com.fancytank.gamegen.programming.data.InputFragment;

public class BlockInputFactory {
    public static BlockInputAppearance create(InputFragment inputFragment) {
        switch (inputFragment.inputType) {
            case DUMMY:
                return new DummyInputAppearance(inputFragment);
            case VARIABLE:
                return new VariableInputAppearance(inputFragment);
            case SOCKET:
                return new SocketInputAppearance(inputFragment);
        }
        return null;
    }
}
