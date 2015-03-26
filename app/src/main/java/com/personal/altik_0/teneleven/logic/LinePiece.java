package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class LinePiece extends GamePiece {
    public LinePiece(int size) {
        pieceGrid = new boolean[1][size];
        for (int i = 0; i < size; i++)
            pieceGrid[0][i] = true;
    }
}
