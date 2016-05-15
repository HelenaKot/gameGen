package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.ConnectionArea;

import static com.fancytank.gamegen.programming.ProgrammingBlock.getProgrammingBlock;

public final class ConnectionRules {

    public static void tryConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
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

    private static boolean isCorrectSignificanceOrder(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return getProgrammingBlock(connectFrom).getSignificance() > getProgrammingBlock(connectTo).getSignificance();
    }

    private static boolean isCorrectVerticalOrder(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return getProgrammingBlock(connectFrom).getSignificance() == getProgrammingBlock(connectTo).getSignificance() && connectFrom.direction == Direction.DOWN;
    }

    private static boolean canConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return notYetConnected(connectFrom, connectTo);
    }

    private static boolean notYetConnected(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return (connectFrom.getConnection() == null && connectTo.getConnection() == null);
    }
}
