package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class ElbowPiece extends GamePiece {
    public ElbowPiece(int size) {
        pieceGrid = new boolean[size][size];

        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                pieceGrid[x][y] = false;

        for (int i = 0; i < size; i++) {
            pieceGrid[0][i] = true;
            pieceGrid[i][0] = true;
        }
    }
}
