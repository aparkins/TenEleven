package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public abstract class GamePiece implements GameGrid {

    protected int[][] pieceGrid;
    protected GameMode mode;

    public int getEntry(int x, int y) {
        return pieceGrid[x][y];
    }

    public int getSize() {
        return Math.max(getWidth(), getHeight());
    }

    public int getWidth() {
        return pieceGrid.length;
    }

    public int getHeight() {
        return pieceGrid[0].length;
    }

    public int getScore() {
        int cntr = 0;
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++)
                cntr += pieceGrid[x][y] != 0 ? 1 : 0;

        return cntr;
    }

    /**
     * Rotates this piece's grid clockwise the specified number of times
     * @param cycles
     */
    public void rotatePiece(int cycles) {
        cycles %= 4;
        for (; cycles > 0; cycles--) {
            int[][] newGrid = new int[getHeight()][getWidth()];

            for (int x = 0; x < getWidth(); x++)
                for (int y = 0; y < getHeight(); y++)
                    newGrid[y][getWidth()-1-x] = pieceGrid[x][y];

            pieceGrid = newGrid;
        }
    }

    protected abstract int[] COLORS();

    public int[][] getGrid() {
        return pieceGrid;
    }

    protected int getColor() {
        int index = getSize();
        if (mode == GameMode.AKIRA)
            index += 6;
        if (mode == GameMode.KURATA)
            index += 12;
        if (mode == GameMode.SHINDO)
            index += 18;
        int color = COLORS()[index];
        if (mode == GameMode.SAI) {
            color = (color & 0x00FFFFFF) | (0x88000000);
        }
        return color;
    }
}
