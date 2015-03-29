package com.personal.altik_0.teneleven.ui.real.views.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import com.personal.altik_0.teneleven.logic.GameGrid;

/**
 * Draws a grid of colored squares! :D
 */
public class GridView extends View {

    private GameGrid grid;

    private float squareDim;
    private int defaultColor;

    private Point tmp;

    public GridView(Context context, GameGrid _grid, int _defaultColor, float _squareDim) {
        super(context);

        grid = _grid;
        defaultColor = _defaultColor;
        squareDim = _squareDim;
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
                int color = grid.getEntry(x,y);
                if (color == 0)
                    p.setColor(defaultColor);
                else
                    p.setColor(color);

                left = offsetX + (squareDim * x) + sqPadding;
                top = offsetY + (squareDim * y) + sqPadding;
                right = left + squareDim - sqPadding;
                bottom = top + squareDim - sqPadding;

                canvas.drawRect(left, top, right, bottom, p);
            }
        }

        p.setColor(0xFFFFFFFF);
        if (tmp != null)
            canvas.drawCircle(tmp.x, tmp.y, 5.0f, p);
    }

    public Point getGridPositionForScreenPosition(float x, float y) {
        tmp = new Point(Math.round(x), Math.round(y));
        invalidate();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        float gridWidth = getGridWidth() * squareDim;
        float gridHeight = getGridHeight() * squareDim;

        x -= (contentWidth * 0.5f) - (gridWidth * 0.5f);
        y -= (contentHeight * 0.5f) - (gridHeight * 0.5f);

        x -= squareDim * 0.5f;
        y -= squareDim * 0.5f;

        x /= squareDim;
        y /= squareDim;

        return new Point(Math.round(x), Math.round(y));
    }

    public int getGridWidth() {
        return grid.getWidth();
    }

    public int getGridHeight() {
        return grid.getHeight();
    }

    public int getDefaultColor() {
        return defaultColor;
    }
}
