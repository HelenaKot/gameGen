package com.fancytank.gamegen.programming.looks.input;


import com.badlogic.gdx.math.Vector2;
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
        return new ConnectionArea(getConnectorPlacement().x, getConnectorPlacement().y, this, Direction.RIGHT);
    }

    @Override
    public Vector2 getConnectorPlacement() {
        return new Vector2(patchData.startX + patchData.width - padding, patchData.startY);
    }
}
