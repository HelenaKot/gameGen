package com.fancytank.gamegen.programming.blocks;

import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.looks.ConnectionArea;

import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public final class ConnectionRules {

    public static void tryConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
        if (isConnected(connectFrom, connectTo)) {
            boolean shouldSwap = false;
            if (isCorrectSignificanceOrder(connectFrom, connectTo))
                shouldSwap = !shouldSwap;
            if (isCorrectVerticalOrder(connectFrom, connectTo))
                shouldSwap = !shouldSwap;

            if (shouldSwap) {
                ConnectionArea tmp = connectFrom;
                connectFrom = connectTo;
                connectTo = tmp;
            }

            if (canConnect(connectFrom, connectTo))
                ProgrammingBlock.attachBlock(connectFrom, connectTo);
        }
    }

    private static boolean isCorrectSignificanceOrder(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return getProgrammingBlock(connectFrom).getSignificance() > getProgrammingBlock(connectTo).getSignificance();
    }

    private static boolean isCorrectVerticalOrder(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return getProgrammingBlock(connectFrom).getSignificance() == getProgrammingBlock(connectTo).getSignificance() && connectFrom.direction == Direction.DOWN;
    }

    private static boolean canConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
        //// TODO: 2016-07-21
        return true;
    }

    private static boolean isConnected(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return (connectFrom.getConnection() == null && connectTo.getConnection() == null);
    }
}
