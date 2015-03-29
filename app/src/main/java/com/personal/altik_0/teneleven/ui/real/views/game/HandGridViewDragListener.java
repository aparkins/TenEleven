package com.personal.altik_0.teneleven.ui.real.views.game;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.personal.altik_0.teneleven.logic.GameManager;

/**
 * Created by Altik_0 on 3/28/2015.
 */
public class HandGridViewDragListener implements View.OnDragListener {

    private GameManager manager;

    public HandGridViewDragListener(GameManager _manager) {
        manager = _manager;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData cd = event.getClipData();
            Bundle bundle = cd.getItemAt(0).getIntent().getExtras();
            Point draggingIndex = new Point(bundle.getInt("draggingIndexX"), bundle.getInt("draggingIndexY"));
            int handPosition = bundle.getInt("handPosition");
            GridView gv = (GridView)v;
            Point droppingIndex = gv.getGridPositionForScreenPosition(event.getX(), event.getY());
            Point playPosition = new Point(droppingIndex.x - draggingIndex.x, droppingIndex.y - draggingIndex.y);
            if (manager.tryPlayPiece(handPosition, playPosition) != 0)
                v.invalidate();
            return true;
        } else if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
            return true;
        }
        return false;
    }
}
