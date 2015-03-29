package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/29/2015.
 */
public class ReloadedPiece extends GamePiece {

    @Override
    protected int[] COLORS() {
        return null;
    }

    public ReloadedPiece(int[][] initGrid, GameMode _mode) {
        mode = _mode;
        pieceGrid = initGrid;
    }
}
