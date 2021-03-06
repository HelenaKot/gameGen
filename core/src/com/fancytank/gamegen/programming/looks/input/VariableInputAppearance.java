package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.Constant;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.Constant.padding;

public class VariableInputAppearance extends DummyInputAppearance {
    VariableInputAppearance(InputFragment inputFragment, CoreBlock parent) {
        super(inputFragment, parent);
    }

    @Override
    public ConnectionArea getConnector() {
        ConnectionArea inputConnector = new ConnectionArea(getConnectorPlacement().x, getConnectorPlacement().y, this, Direction.RIGHT);
        inputFragment.setConnectionArea(inputConnector);
        return inputConnector;
    }

    @Override
    public Vector2 getConnectorPlacement() {
        return new Vector2(patchData.startX + patchData.width - padding, patchData.startY - Constant.spacing + spacing);
    }
}
