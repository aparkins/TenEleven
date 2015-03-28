package com.personal.altik_0.teneleven.ui.real.views.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Draws a grid of colored squares! :D
 */
public class GridView extends View {
    private int[][] colorGrid;
    private int defaultColor;

    private float squareDim;

    public GridView(Context context, int width, int height, int _defaultColor, float _squareDim) {
        super(context);

        colorGrid = new int[width][height];
        defaultColor = _defaultColor;
        squareDim = _squareDim;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                colorGrid[x][y] = defaultColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        float gridWidth = getGridWidth() * squareDim;
        float gridHeight = getGridHeight() * squareDim;

        float offsetX = (contentWidth * 0.5f) - (gridWidth * 0.5f);
        float offsetY = (contentHeight * 0.5f) - (gridHeight * 0.5f);

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);

        float left, top, right, bottom;
        float sqPadding = squareDim * 0.05f;
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                p.setColor(colorGrid[x][y]);

                left = offsetX + (squareDim * x) + sqPadding;
                top = offsetY + (squareDim * y) + sqPadding;
                right = left + squareDim - sqPadding;
                bottom = top + squareDim - sqPadding;

                canvas.drawRect(left, top, right, bottom, p);
            }
        }
    }

    public int getGridWidth() {
        return colorGrid.length;
    }

    public int getGridHeight() {
        return colorGrid[0].length;
    }

    public void setEntry(int x, int y, int color) {
        colorGrid[x][y] = color;
    }

    public void unsetEntry(int x, int y) {
        colorGrid[x][y] = defaultColor;
    }
}
