package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class LinePiece extends GamePiece {

    private static final int[] COLORS = { 0, 0, 0xFFFF0088, 0xFFFF3388, 0xFFFF66AA, 0xFFFF88CC };

    public LinePiece(int size) {
        pieceGrid = new int[1][size];
        for (int i = 0; i < size; i++)
            pieceGrid[0][i] = COLORS[size];
    }
}
