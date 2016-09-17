package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.script.Executable;
import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.LinkedList;
import java.util.List;

import static com.fancytank.gamegen.game.actor.ActorInitializer.getActionsPerTick;
import static com.fancytank.gamegen.game.actor.ActorInitializer.getListenerList;

public abstract class BaseActor extends Actor {
    public int x, y;
    public Color tint;
    protected String className;
    private LinkedList<Executable> actions = new LinkedList<>();

    /**
     * najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
     */
    public BaseActor(int x, int y, String className) {
        this.x = x;
        this.y = y;
        this.className = className;
        tint = Color.WHITE;
        setBounds(getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        for (InputListener listener : initListenersList())
            addListener(listener);
        actions = initActionsPerTick();
    }

    @Override
    public void act(float delta) {
        for (Executable action : actions)
            action.performAction();
    }

    private LinkedList<Executable> initActionsPerTick() {
        LinkedList<Executable> output = new LinkedList<>();
        for (ExecutableProducer producer : getActionsPerTick(className)) {
            Executable instance = producer.getInstance();
            instance.init(this);
            output.add(instance);
        }
        return output;
    }

    private LinkedList<InputListener> initListenersList() {
        List<ExecutableProducer> executables = getListenerList(className);
        LinkedList<InputListener> output = new LinkedList<>();
        final BaseActor local = this;

        for (ExecutableProducer executableClass : executables) {
            final Executable myInstance = executableClass.getInstance();
            myInstance.init(local);
            output.add(new InputListener() {
                           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                               myInstance.performAction();
                               return true;
                           }
                       }
            );
        }
        return output;
    }

    @Override
    public boolean remove() {
        actions = new  LinkedList<>();
        return super.remove();
    }

    public String getClassName() {
        return className;
    }
}
