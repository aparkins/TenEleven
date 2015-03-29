package com.personal.altik_0.teneleven.ui.real.views.game;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;

import com.personal.altik_0.teneleven.logic.GameGrid;

/**
 * Draws a grid of colored squares! :D
 */
public class GridView extends View {

    private static final int MAX_FRAME = 300000000;
    private static final int MID_FRAME = 150000000;
    private int curFrame = 0;

    private GameGrid grid;

    private float squareDim;
    private int defaultColor;

    private Point tmp;

    private int[][] cachedColors = null;

    public GridView(Context context, GameGrid _grid, int _defaultColor, float _squareDim) {
        super(context);

        grid = _grid;
        defaultColor = _defaultColor;
        squareDim = _squareDim;
    }

    public void setSparkEnabled(boolean flag) {
        if (flag)
            cachedColors = new int[grid.getWidth()][grid.getHeight()];
        else
            cachedColors = null;
    }

    public void drawGlow(Canvas canvas, boolean enableGlow) {
        if (enableGlow) {
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
            p.setColor(0xFFFFFFFF);
            p.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

            RectF box = new RectF();
            float r = squareDim * 0.1f;
            for (int x = 0; x < getGridWidth(); x++) {
                for (int y = 0; y < getGridHeight(); y++) {
                    int color = grid.getEntry(x, y);
                    if (color != 0) {
                        box.left = offsetX + (squareDim * x);
                        box.top = offsetY + (squareDim * y);
                        box.right = box.left + squareDim;
                        box.bottom = box.top + squareDim;

                        canvas.drawRoundRect(box, r, r, p);
                    }
                }
            }
        }
        draw(canvas);
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

        RectF box = new RectF();
        float r = squareDim * 0.1f;
        float sparkMaxX, sparkMaxY, sparkCenterX, sparkCenterY, animRatio;
        float sqPadding = squareDim * 0.06f;
        boolean triggerInvalidate = false;

        p.setColor(defaultColor);
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                box.left = offsetX + (squareDim * x) + sqPadding;
                box.top = offsetY + (squareDim * y) + sqPadding;
                box.right = box.left + squareDim - sqPadding;
                box.bottom = box.top + squareDim - sqPadding;

                canvas.drawRoundRect(box, r, r, p);
            }
        }

        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                int color = grid.getEntry(x, y);
                if (color != 0) {
                    box.left = offsetX + (squareDim * x) + sqPadding;
                    box.top = offsetY + (squareDim * y) + sqPadding;
                    box.right = box.left + squareDim - sqPadding;
                    box.bottom = box.top + squareDim - sqPadding;
                    p.setColor(color);
                    canvas.drawRoundRect(box, r, r, p);
                }
            }
        }

        if (cachedColors != null) {
            triggerInvalidate = true;
            for (int x = 0; x < getGridWidth(); x++) {
                for (int y = 0; y < getGridHeight(); y++) {
                    int color = grid.getEntry(x, y);
                    box.left = offsetX + (squareDim * x) + sqPadding;
                    box.top = offsetY + (squareDim * y) + sqPadding;
                    box.right = box.left + squareDim - sqPadding;
                    box.bottom = box.top + squareDim - sqPadding;

                    if (cachedColors[x][y] != color) {
                        p.setColor(0xFFFFFF00);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(2.0f);

                        sparkCenterX = box.centerX();
                        sparkCenterY = box.centerY();
                        animRatio = (float) Math.abs(curFrame - MID_FRAME) / (float) MID_FRAME;
                        for (float i = 0; i < Math.PI * 2.0f; i += (Math.PI * 0.02f)) {

                            sparkMaxX = ((float) Math.cos(i)) * (squareDim * 1.05f) * animRatio;
                            sparkMaxY = ((float) Math.sin(i)) * (squareDim * 1.05f) * animRatio;
                            sparkMaxX += sparkCenterX;
                            sparkMaxY += sparkCenterY;

                            canvas.drawLine(sparkCenterX, sparkCenterY, sparkMaxX, sparkMaxY, p);
                        }

                        p.setStyle(Paint.Style.FILL);
                        curFrame++;
                        if (curFrame > MAX_FRAME) {
                            curFrame = 0;
                            triggerInvalidate = false;
                        }
                    }
                }
            }

            for (int x = 0; x < getGridWidth(); x++) {
                for (int y = 0; y < getGridHeight(); y++) {
                    int color = grid.getEntry(x, y);
                    if (cachedColors[x][y] != color) {
                        box.left = offsetX + (squareDim * x) + sqPadding;
                        box.top = offsetY + (squareDim * y) + sqPadding;
                        box.right = box.left + squareDim - sqPadding;
                        box.bottom = box.top + squareDim - sqPadding;
                        p.setColor(color);
                        canvas.drawRoundRect(box, r, r, p);
                        cachedColors[x][y] = color;
                    }
                }
            }
        }

        if (triggerInvalidate)
            invalidate();

        // Debug circle
        //p.setColor(0xFFFFFFFF);
        //if (tmp != null)
        //    canvas.drawCircle(tmp.x, tmp.y, 5.0f, p);
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

    public static class GridViewShadowBuilder extends View.DragShadowBuilder {
        private Point initialTouchPoint;
        private boolean enableGlow;

        public GridViewShadowBuilder(View v, Point _initialTouchPoint, boolean _enableGlow) {
            super(v);
            initialTouchPoint = _initialTouchPoint;
            enableGlow = _enableGlow;
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            getView().setAlpha(1);
            ((GridView)getView()).drawGlow(canvas, enableGlow);
        }

        @Override
        public void onProvideShadowMetrics (Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            shadowTouchPoint.set(initialTouchPoint.x, initialTouchPoint.y);
        }
    }
}
