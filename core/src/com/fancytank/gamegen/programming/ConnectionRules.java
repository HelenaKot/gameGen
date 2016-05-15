package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.ConnectionArea;

import static com.fancytank.gamegen.programming.ProgrammingBlock.getProgrammingBlock;

public final class ConnectionRules {

    public static void tryConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
        boolean shouldSwap = false;
        // honour significance
        if (getProgrammingBlock(connectFrom).getSignificance() > getProgrammingBlock(connectTo).getSignificance())
            shouldSwap = !shouldSwap;

        // check vertical up to down significance
        if (getProgrammingBlock(connectFrom).getSignificance() == getProgrammingBlock(connectTo).getSignificance() && connectFrom.direction == Direction.DOWN)
            shouldSwap = !shouldSwap;

        if (shouldSwap) {
            ConnectionArea tmp = connectFrom;
            connectFrom = connectTo;
            connectTo = tmp;
        }

        if (canConnect(connectFrom, connectTo))
            ProgrammingBlock.attachBlock(connectFrom, connectTo);
    }

    private static boolean canConnect(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return notYetConnected(connectFrom, connectTo);
    }

    private static boolean notYetConnected(ConnectionArea connectFrom, ConnectionArea connectTo) {
        return (connectFrom.getConnection() == null && connectTo.getConnection() == null);
    }
}
