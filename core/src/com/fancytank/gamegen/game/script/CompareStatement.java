package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;

import java.util.Vector;

class CompareStatement implements Executable {
    private BlockData blockData;
    Variable var0, var1;
    Compare statement;

    public CompareStatement(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor block) {
        Vector<Variable> vars = Util.collectVars(blockData);
        var0 = vars.get(0);
        var1 = vars.get(1);
        switch (blockData.getValue().charAt(0)) {
            case '<':
                statement = (var1, var2) -> 0 > var1.compareTo(var2);
                break;
            case '=':
                statement = (var1, var2) -> 0 == var1.compareTo(var2);
                break;
            case '>':
                statement = (var1, var2) -> 0 < var1.compareTo(var2);
                break;
            default:
                statement = (var1, var2) -> false;
                break;
        }
    }

    @Override
    public boolean performAction() {
        return statement.compare(var0, var1);
    }

    private interface Compare {
        boolean compare(Variable var0, Variable var1);
    }
}
