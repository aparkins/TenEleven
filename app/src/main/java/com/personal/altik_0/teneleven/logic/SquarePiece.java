package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class SquarePiece extends GamePiece {

    @Override
    protected int[] COLORS() {
        return new int[] { 0, 0xFF00FF66, 0xFF66FFAA, 0xFF88FFCC, 0, 0,     // NORMAL
                           0, 0xFF22CC55, 0xFF33EE77, 0xFF55FF88, 0, 0,     // AKIRA
                           0, 0xFF777777, 0xFF777777, 0xFF777777, 0, 0,     // KURATA
                           0, 0xFF101010, 0xFF181818, 0xFF202020, 0, 0,     // SHINDO
        };
    }

    public SquarePiece(int size, GameMode _mode) {
        mode = _mode;
        pieceGrid = new int[size][size];
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                pieceGrid[x][y] = getColor();
    }
}
