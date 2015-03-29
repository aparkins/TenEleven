package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class SquarePiece extends GamePiece {

    private static final int[] COLORS = { 0, 0xFF00FF66, 0xFF66FFAA, 0xFF88FFCC };

    public SquarePiece(int size) {
        pieceGrid = new int[size][size];
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                pieceGrid[x][y] = COLORS[size];
    }
}
