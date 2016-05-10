package com.fancytank.gamegen.programming.looks.input;

import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.CoreBlock;

public final class BlockInputFactory {
    public static BlockInputAppearance create(InputFragment inputFragment, CoreBlock parent) {
        switch (inputFragment.inputType) {
            case DUMMY:
                return new DummyInputAppearance(inputFragment, parent);
            case VARIABLE:
                return new VariableInputAppearance(inputFragment, parent);
            case SOCKET:
                return new SocketInputAppearance(inputFragment, parent);
        }
        return null;
    }
}
