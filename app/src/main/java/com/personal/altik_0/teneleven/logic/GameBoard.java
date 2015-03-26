package com.personal.altik_0.teneleven.logic;

import android.graphics.Point;

import java.util.List;
import java.util.Stack;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class GameBoard {

    private boolean[][] grid;

    public GameBoard(int width, int height) {
        grid = new boolean[width][height];
    }

    public boolean getEntry(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public List<Integer> getClearedColumns() {
        Stack<Integer> list = new Stack<>();
        for (int x = 0; x < getWidth(); x++) {
            boolean flag = true;
            for (int y = 0; y < getHeight(); y++)
                if (!grid[x][y]) {
                    flag = false;
                    break;
                }
            if (flag)
                list.push(x);
        }

        return list;
    }

    public List<Integer> getClearedRows() {
        Stack<Integer> list = new Stack<>();
        for (int y = 0; y < getHeight(); y++) {
            boolean flag = true;
            for (int x = 0; x < getWidth(); x++)
                if (!grid[x][y]) {
                    flag = false;
                    break;
                }
            if (flag)
                list.push(y);
        }

        return list;
    }

    public int tryPlacePiece(GamePiece piece, Point position) {
        if (!canPlacePieceInPosition(piece, position))
            return 0;

        for (int x = 0; x < piece.getWidth(); x++)
            for (int y = 0; y < piece.getHeight(); y++)
                grid[x + position.x][y + position.y] |= piece.getEntry(x, y);

        // compute score from play:
        int points = piece.getScore();
        List<Integer> clearedCols = getClearedColumns();
        List<Integer> clearedRows = getClearedRows();
        points += (clearedCols.size() * getHeight());
        points += (getWidth() - clearedCols.size()) * clearedRows.size();

        // clear out the appropriate columns and rows:
        for (Integer col : clearedCols)
            for (int y = 0; y < getHeight(); y++)
                grid[col][y] = false;
        for (Integer row : clearedRows)
            for (int x = 0; x < getWidth(); x++)
                grid[x][row] = false;

        return points;
    }

    public boolean canPlacePiece(GamePiece piece) {
        int finalX = getWidth() - piece.getWidth();
        int finalY = getHeight() - piece.getHeight();

        for (int x = 0; x < finalX; x++)
            for (int y = 0; y < finalY; y++)
                if (canPlacePieceInPosition(piece, new Point(x, y)))
                    return true;

        return false;
    }

    public boolean canPlacePieceInPosition(GamePiece piece, Point position) {
        boolean flag = true;
        for (int x = position.x; x < position.x + piece.getWidth(); x++)
            for (int y = position.y; y < position.y + piece.getHeight(); y++)
                flag &= !(piece.getEntry(x,y) && getEntry(x,y));
        return flag;
    }
}
