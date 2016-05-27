package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockAppearance;

import java.util.ArrayList;

public class InputContent {
    ArrayList<Widget> content = new ArrayList<Widget>();
    private float width = 0, height = 0;
    private InputFragment inputFragment;
    private static Label.LabelStyle labelStyle = new Label.LabelStyle(BlockAppearance.font, Color.BLACK);
    /*
    private static TextField.TextFieldStyle textFieldStyle;
    private static SelectBox.SelectBoxStyle selectBoxStyle;
    */

    public InputContent(InputFragment inputFragment, String labelText) {
        this.inputFragment = inputFragment;
//        if (textFieldStyle == null)
//            init();
        addLabel(labelText);
    }

    public InputContent addLabel(String labelText) {
        return addComponent(new Label(labelText, labelStyle));
    }

    //todo work on this
    /*
    public InputContent addTextField(String text) {
        TextField textfield = new TextField(text, textFieldStyle);
        textfield.setAlignment(Align.center);
        textfield.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
            }
        });
        return addComponent(textfield);
    }

    public InputContent addSelectBox(String[] itemLabels) {
        SelectBox selectBox = new SelectBox(selectBoxStyle);
        selectBox.setItems(itemLabels);
        return addComponent(selectBox);
    }
*/

    private InputContent addComponent(Widget widget) {
        content.add(widget);
        recalculate();
        return this;
    }

    private void updateInputData() {

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
/*
    private static void init() {
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = BlockAppearance.font;
        textFieldStyle.fontColor = Color.BLACK;
        selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.font = BlockAppearance.font;
        selectBoxStyle.fontColor = Color.BLACK;
        Drawable placehodlerImage = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg"))));
        selectBoxStyle.background = placehodlerImage;
        selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        selectBoxStyle.scrollStyle.background = placehodlerImage;
        selectBoxStyle.listStyle = new List.ListStyle();
        selectBoxStyle.listStyle.background = placehodlerImage;
    }
*/
}
