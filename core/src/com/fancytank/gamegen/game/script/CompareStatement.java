package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;

import java.util.Vector;

class CompareStatement implements Executable {
    private BlockData blockData;
    Vector<Variable> vars;
    Compare statement;

    public CompareStatement(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor block) {
        vars = Util.collectVars(blockData);
        switch (blockData.getValue().charAt(0)) {
            case '<':
                statement = new Compare() {
                    @Override
                    public boolean compare(Variable var1, Variable var2) {
                        return 0 > var1.compareTo(var2);
                    }
                };
                break;
            case '=':
                statement = new Compare() {
                    @Override
                    public boolean compare(Variable var1, Variable var2) {
                        return 0 == var1.compareTo(var2);
                    }
                };
                break;
            case '>':
                statement = new Compare() {
                    @Override
                    public boolean compare(Variable var1, Variable var2) {
                        return 0 < var1.compareTo(var2);
                    }
                };
                break;
            default:
                statement = new Compare() {
                    @Override
                    public boolean compare(Variable var1, Variable var2) {
                        return false;
                    }
                };
                break;
        }
    }

    @Override
    public boolean performAction() {
        return statement.compare(vars.get(0), vars.get(1));
    }

    private interface Compare {
        boolean compare(Variable var1, Variable var2);
    }
}
