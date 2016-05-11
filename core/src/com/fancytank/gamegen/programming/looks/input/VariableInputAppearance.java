package com.fancytank.gamegen.programming.looks.input;


import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class VariableInputAppearance extends DummyInputAppearance {
    VariableInputAppearance(InputFragment inputFragment, CoreBlock parent) {
        super(inputFragment, parent);
    }

    @Override
    public ConnectionArea getConnectors() {
        return new ConnectionArea(patchData.startX + patchData.width - padding, patchData.startY , parent, Direction.RIGHT);
    }
}
