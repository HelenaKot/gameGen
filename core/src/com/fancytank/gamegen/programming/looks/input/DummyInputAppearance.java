package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.Constant;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.Constant.padding;

public class DummyInputAppearance extends BlockInputAppearance {
    InputContent inputContent;
    int spacing = Constant.spacing;

    DummyInputAppearance(InputFragment inputFragment, CoreBlock parent) {
        super(inputFragment, parent);
        inputContent = new InputContent(inputFragment.labelText);
        if (coreBlock.data.shape == BlockShape.VARIABLE)
            spacing = 0;
        setPreferredSize(spacing);
    }

    private void setPreferredSize(int spacing) {
        patchData.width = inputContent.getWidth() + padding + spacing;
        patchData.height = inputContent.getHeight() + spacing;
    }

    @Override
    public void drawInput(Batch batch, float alpha) {
        super.drawInput(batch, alpha);
        inputContent.draw(batch, alpha);
    }

    @Override
    public ConnectionArea getConnector() {
        return null;
    }

    @Override
    public Vector2 getConnectorPlacement() {
        return null;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        inputContent.setPosition(x + spacing / 2, y);
    }
}
