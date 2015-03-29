package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/28/2015.
 */
public interface GameGrid {
    int getWidth();
    int getHeight();
    int getEntry(int x, int y);
    int[][] getGrid();
}
