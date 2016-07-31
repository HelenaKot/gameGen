package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.script.ExecutableProducer.ActionListenerType;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.Vector;

public class Util {
    static ExecutableProducer createSubBlock(BlockData blockData) {
        if (blockData != null) {
            return new ExecutableProducer(blockData, ActionListenerType.NONE);
        }
        return null;
    }

    static Executable initFromProducer(ExecutableProducer producer, BaseActor actor) {
        if (producer == null)
            return null;
        Executable executable = producer.getInstance();
        executable.init(actor);
        return executable;
    }

}
