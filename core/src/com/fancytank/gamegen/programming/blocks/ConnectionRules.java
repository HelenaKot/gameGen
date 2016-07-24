package com.fancytank.gamegen.programming.blocks;

import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.looks.ConnectionArea;

import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public final class ConnectionRules {

    public static void tryConnect(ConnectionArea base, ConnectionArea attaching) {
        if (isConnected(base, attaching)) {
            boolean shouldSwap = false;
            if (!isCorrectSignificanceOrder(base, attaching))
                shouldSwap = !shouldSwap;
            if (!isCorrectVerticalOrder(base, attaching))
                shouldSwap = !shouldSwap;

            if (shouldSwap) {
                ConnectionArea tmp = base;
                base = attaching;
                attaching = tmp;
            }

            if (canConnect(base, attaching))
                ProgrammingBlock.attachBlock(base, attaching);
        }
    }

    private static boolean isCorrectSignificanceOrder(ConnectionArea base, ConnectionArea attaching) {
        return getProgrammingBlock(base).getSignificance() >= getProgrammingBlock(attaching).getSignificance();
    }

    private static boolean isCorrectVerticalOrder(ConnectionArea base, ConnectionArea attaching) {
        return getProgrammingBlock(base).getSignificance() != getProgrammingBlock(attaching).getSignificance()
                || (base.direction == Direction.DOWN || base.direction == Direction.RIGHT);
    }

    private static boolean canConnect(ConnectionArea base, ConnectionArea attaching) {
        //// TODO: 2016-07-21
        return true;
    }

    private static boolean isConnected(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return (connectFrom.getConnection() == null && connectTo.getConnection() == null);
    }
}
