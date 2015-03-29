package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class LinePiece extends GamePiece {

    @Override
    protected int[] COLORS() {
        return new int[] { 0, 0, 0xFFFF0088, 0xFFFF3388, 0xFFFF66AA, 0xFFFF88CC,    // NORMAL
                           0, 0, 0xFF2255AA, 0xFF3377CC, 0xFF4488EE, 0xFF6699FF,    // AKIRA
                           0, 0, 0xFF777777, 0xFF777777, 0xFF777777, 0xFF777777,    // KURATA
                           0, 0, 0xFF101010, 0xFF181818, 0xFF202020, 0xFF282828,    // SHINDO
        };
    }

    public LinePiece(int size, GameMode _mode) {
        mode = _mode;
        pieceGrid = new int[1][size];
        for (int i = 0; i < size; i++)
            pieceGrid[0][i] = getColor();
    }
}
