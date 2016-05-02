package com.fancytank.gamegen.generated;

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SampleActor extends BaseActor {

    public SampleActor() {
        super("badlogic.jpg","#ffffff");

        SampleActor.addActionListener(new InputListener(

        ));
    }

//    public void draw(Batch batch, float alpha) {
//        batch.setColor(tint);
//        batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
//                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
//                texture.getWidth(), texture.getHeight(), false, false);
//        // TODO
//        /* or for textureAtlas fragment:
//            batch.draw(blockData.atlasRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
//                getHeight(), getScaleX(), getScaleY(), getRotation());
//         */
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        // TODO
//    }

}
