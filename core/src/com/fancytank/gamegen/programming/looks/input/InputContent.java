package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockAppearance;

import java.util.ArrayList;

public class InputContent {
    ArrayList<Widget> content = new ArrayList<Widget>();
    private float width = 0, height = 0;
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(BlockAppearance.font, Color.BLACK);

    public InputContent(InputFragment inputFragment, String labelText) {
        addLabel(labelText);
    }

    public InputContent addLabel(String labelText) {
        return addComponent(new Label(labelText, labelStyle));
    }

    private InputContent addComponent(Widget widget) {
        content.add(widget);
        recalculate();
        return this;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setPosition(float x, float y) {
        content.get(0).setPosition(x, y);
        for (int i = 1; i < content.size(); i++)
            content.get(i).setPosition(content.get(i - 1).getRight(), y);
    }

    public void draw(Batch batch, float alpha) {
        for (Widget widget : content) {
            widget.draw(batch, alpha);
        }
    }

    private void recalculate() {
        width = 0;
        for (Widget widget : content) {
            width += widget.getWidth();
            if (widget.getHeight() > height)
                height = widget.getHeight();
        }
        setPosition(content.get(0).getX(), content.get(0).getY());
    }
}
