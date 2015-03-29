package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class ElbowPiece extends GamePiece {

    private static final int[] COLORS = { 0, 0, 0xFF88FF00, 0xFFCCFF66 };

    public ElbowPiece(int size) {
        pieceGrid = new int[size][size];

        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                pieceGrid[x][y] = 0;

        for (int i = 0; i < size; i++) {
            pieceGrid[0][i] = COLORS[size];
            pieceGrid[i][0] = COLORS[size];
        }
    }
}
