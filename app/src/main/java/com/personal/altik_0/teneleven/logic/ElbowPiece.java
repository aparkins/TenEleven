package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class ElbowPiece extends GamePiece {

    @Override
    protected int[] COLORS() {
        return new int[] { 0, 0, 0xFF88FF00, 0xFFCCFF66, 0, 0,      // NORMAL
                           0, 0, 0xFF99FFFF, 0xFFCCFFFF, 0, 0,      // AKIRA
                           0, 0, 0xFF777777, 0xFF777777, 0, 0,      // KURATA
        };
    }

    public ElbowPiece(int size, GameMode _mode) {
        mode = _mode;
        pieceGrid = new int[size][size];

        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                pieceGrid[x][y] = 0;

        int color = getColor();
        for (int i = 0; i < size; i++) {
            pieceGrid[0][i] = color;
            pieceGrid[i][0] = color;
        }
    }
}
