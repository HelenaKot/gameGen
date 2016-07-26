package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.GenericActor;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.MethodType;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.Vector;

public class ExecutableProducer {
    enum ActionListenerType {ON_PRESS, ON_RELEASE, ON_HOLD, TICK, NONE} //todo not used yet

    BlockData methodBlock;
    ActionListenerType type;
    MethodType methodType;

    private ExecutableProducer conditionProducer, executionProducer;

    ExecutableProducer(BlockData methodBlock, ActionListenerType type) {
        this.methodBlock = methodBlock;
        this.type = type;
        methodType = methodBlock.getExpectedMethod();
    }

    public Executable getInstance() {
        switch (methodType) {
            case BLOCK_SETTER:
                return getBlockSetter();
            case COLOR_SETTER:
                return getBlockColorChanger();
            case VARIABLE_SETTER:
                return getVariableSetter();
            case COMPARE_STATEMENT:
                return getCompareStatement();
            case LOOP_WHILE:
                return getWhileStatement();
            case LOOP_FOR:
                return getForStatement();
            case IF_STATEMENT:
                return getIfStatement();
            default:
                return getDefault();
        }
    }

    private Executable getDefault() {
        return new Executable() {
            Variable variable;

            @Override
            public void init(BaseActor blockInstance) {
                variable = methodBlock.getVariable();
            }

            @Override
            public boolean performAction() {
                return Boolean.parseBoolean(variable.getValue());
            }
        };
    }

    private Executable getBlockSetter() {
        return new Executable() {
            BaseActor blockInstance;
            Variable blockClassName;
            Variable blockX, blockY;

            @Override
            public void init(BaseActor blockInstance) {
                System.out.println(methodBlock);
                this.blockInstance = blockInstance;
                Vector<Variable> vars = collectVars();
                blockClassName = vars.get(0);
                blockX = vars.get(1);
                blockY = vars.get(2);
            }

            @Override
            public boolean performAction() {
                MapManager.changeBlock(
                        ActorInitializer.getInstanceOf(blockClassName.getValue(), blockInstance.x + blockX.getInt(), blockInstance.y + blockY.getInt()));
                return true;
            }
        };
    }

    private Executable getBlockColorChanger() {
        return new Executable() {
            BaseActor blockInstance;
            Vector<Variable> vars;
            Variable color;

            @Override
            public void init(BaseActor blockInstance) {
                this.blockInstance = blockInstance;
                vars = collectVars();
                color = vars.get(0);
            }

            @Override
            public boolean performAction() {
                if (blockInstance instanceof GenericActor)
                    ((GenericActor) blockInstance).tint = Color.valueOf(color.getValue());
                return true;
            }
        };
    }

    private Executable getVariableSetter() {
        return new Executable() {
            Variable variableToSet;
            Variable newValue;

            @Override
            public void init(BaseActor blockInstance) {
                variableToSet = methodBlock.getVariable();
                BlockData connectedTo = methodBlock.getInputs()[0].connectedTo;
                if (connectedTo != null)
                    newValue = connectedTo.getVariable();
            }

            @Override
            public boolean performAction() {
                VariableList.put(variableToSet.getDirectValue(), newValue);
                return true;
            }
        };
    }

    private Executable getCompareStatement() {
        return new Executable() {
            Vector<Variable> vars;
            Compare statement;

            @Override
            public void init(BaseActor block) {
                vars = collectVars();
                switch (methodBlock.getValue().charAt(0)) {
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
        };
    }

    private interface Compare {
        boolean compare(Variable var1, Variable var2);
    }

    private Executable getIfStatement() {
        return new Executable() {
            Boolean doElse = false;
            Executable condition, execution1, execution2;

            @Override
            public void init(BaseActor blockInstance) {
                condition = createSubBlock(methodBlock.getInputs()[0].connectedTo).getInstance();
                condition.init(blockInstance);
                execution1 = createSubBlock(methodBlock.getInputs()[1].connectedTo).getInstance();
                execution1.init(blockInstance);
                if (methodBlock.getInputs().length > 2) {
                    execution2 = createSubBlock(methodBlock.getInputs()[3].connectedTo).getInstance();
                    execution2.init(blockInstance);
                    doElse = true;
                }
            }

            @Override
            public boolean performAction() {
                if (condition.performAction())
                    execution1.performAction();
                else if (doElse)
                    execution2.performAction();
                return true;
            }
        };
    }

    private Executable getWhileStatement() {
        LoopType whileLoop = new LoopType() {
            public void execute(Executable condition, Executable execute) {
                while (condition.performAction())
                    execute.performAction();
            }
        };
        setConditionProducer(methodBlock.getInputs()[0].connectedTo);
        return getGenericLoop(conditionProducer, whileLoop);
    }

    private Executable getForStatement() {
        String value;
        if (methodBlock.hasValue()) {
            value = methodBlock.getValue();
            return getForTimes(Integer.parseInt(value));
            //todo dla listy
        }
        return null;
    }

    private Executable getForTimes(final int numericValue) {
        LoopType forLoop = new LoopType() {
            public void execute(Executable condition, Executable execute) {
                for (int i = 0; i < numericValue; i++) {
                    execute.performAction();
                }
            }
        };
        return getGenericLoop(null, forLoop);
    }

    private Executable getGenericLoop(final ExecutableProducer conditionProducer, final LoopType loop) {
        return new Executable() {
            Executable condition;
            Executable execute;

            @Override
            public void init(BaseActor block) {
                if (executionProducer == null)
                    executionProducer = createSubBlock(methodBlock.getInputs()[1].connectedTo);
                execute = executionProducer.getInstance();
                execute.init(block);
                if (conditionProducer != null) {
                    condition = conditionProducer.getInstance();
                    condition.init(block);
                }
            }

            @Override
            public boolean performAction() {
                loop.execute(condition, execute);
                return true;
            }
        };
    }

    private void setConditionProducer(BlockData methodBlock) {
        if (conditionProducer == null)
            conditionProducer = createSubBlock(methodBlock);
    }

    private interface LoopType {
        void execute(final Executable condition0, final Executable execute0);
    }

    private ExecutableProducer createSubBlock(BlockData blockData) {
        if (blockData != null) {
            return new ExecutableProducer(blockData, ActionListenerType.NONE);
        }
        return null;
    }

    private Vector<Variable> collectVars() {
        Vector<Variable> vars = new Vector<Variable>();
        InputFragment[] inputs = methodBlock.getInputs();
        for (int i = 0; i < inputs.length; i++)
            if (inputs[i].inputType != InputType.DUMMY)
                vars.add((inputs[i].connectedTo != null) ? inputs[i].connectedTo.getVariable() : new Variable("0", ValueType.ANY));
        return vars;
    }
}
