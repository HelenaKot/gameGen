package com.fancytank.gamegen.programming.blocks;

import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.looks.ConnectionArea;

import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public final class ConnectionRules {

    public static void tryConnect(ConnectionArea base, ConnectionArea attaching) {
        if (notConnected(base, attaching)) {
            if (shouldSwap(base, attaching)) {
                ConnectionArea tmp = base;
                base = attaching;
                attaching = tmp;
            }

            if (canConnect(base, attaching))
                ProgrammingBlock.attachBlock(base, attaching);
        }
    }

    private static boolean shouldSwap(ConnectionArea base, ConnectionArea attaching) {
        boolean shouldSwap = false;
        if (!isCorrectSignificanceOrder(base, attaching))
            shouldSwap = !shouldSwap;
        if (!isCorrectVerticalOrder(base, attaching))
            shouldSwap = !shouldSwap;
        return shouldSwap;
    }

    private static boolean isCorrectSignificanceOrder(ConnectionArea base, ConnectionArea attaching) {
        return getProgrammingBlock(base).getSignificance() >= getProgrammingBlock(attaching).getSignificance();
    }

    private static boolean isCorrectVerticalOrder(ConnectionArea base, ConnectionArea attaching) {
        return getProgrammingBlock(base).getSignificance() != getProgrammingBlock(attaching).getSignificance()
                || (base.direction == Direction.DOWN || base.direction == Direction.RIGHT);
    }

    private static boolean canConnect(ConnectionArea base, ConnectionArea attaching) {
        return (matchingValue(base.getAcceptedValueType(), attaching.getInnerValueType()));
    }

    private static boolean matchingValue(ValueType base, ValueType attaching) {
        return (base == attaching || base == ValueType.ANY || attaching == ValueType.ANY || isNumberValue(base) && isNumberValue(attaching));
    }

    private static boolean isNumberValue(ValueType valueType) {
        return (valueType == ValueType.NUMBER || valueType == ValueType.INT_NUMBER);
    }

    private static boolean notConnected(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return (connectFrom.getConnection() == null && connectTo.getConnection() == null);
    }
}
