package com.fancytank.gamegen.programming.looks.input;


import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class SocketInputAppearance extends BlockInputAppearance {

    SocketInputAppearance(InputFragment inputFragment, CoreBlock parent) {
        super(inputFragment, parent);
        setPreferredSize();
    }

    @Override
    public ConnectionArea getConnectors() {
        return new ConnectionArea(getConnectorPlacement().x, getConnectorPlacement().y, this, Direction.DOWN);
    }

    @Override
    public Vector2 getConnectorPlacement() {
        return new Vector2(patchData.startX, patchData.startY + patchData.height - padding);
    }

    void setPreferredSize() {
        patchData.height = padding * 2;
    }

}
