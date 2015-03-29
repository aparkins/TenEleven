package com.personal.altik_0.teneleven.ui.real;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.altik_0.teneleven.R;
import com.personal.altik_0.teneleven.logic.GameBoard;
import com.personal.altik_0.teneleven.logic.GameManager;
import com.personal.altik_0.teneleven.logic.GameMode;
import com.personal.altik_0.teneleven.logic.GamePiece;
import com.personal.altik_0.teneleven.ui.real.views.game.GridView;

import java.util.List;

public class GameActivity extends Activity implements OptionsDialogFragment.OnSelectOptionListener {

    GameManager manager;

    private static final int BACKGROUND_COLOR = 0xFF333333;
    private float SQUARE_DIM;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBundle("gameData", manager.saveInstanceState());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null && savedInstanceState.getBundle("gameData") != null) {
            Bundle gameData = savedInstanceState.getBundle("gameData");
            manager = new GameManager(gameData);
        } else {
            manager = new GameManager(GameMode.NORMAL);
        }


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int minDim = Math.min(size.x, size.y);
        SQUARE_DIM = minDim * 0.055f;

        View topLayer = findViewById(R.id.gameLayout);
        topLayer.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (event.getAction() == DragEvent.ACTION_DRAG_EXITED ||
                    event.getAction() == DragEvent.ACTION_DRAG_ENDED ||
                    event.getAction() == DragEvent.ACTION_DROP) {
                    refreshGameViews();
                }
                return true;
            }
        });

        initializeGameView();
        initializeGearIcon();
    }

    private void initializeGearIcon() {
        ImageButton gear = (ImageButton)findViewById(R.id.optionsButton);
        gear.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsDialogFragment optionsMenu = new OptionsDialogFragment();
                optionsMenu.show(getFragmentManager(), "Menu Dialog Fragment");
            }
        });
    }

    private void initializeGameView() {
        initializeBoardGrid();
        initializeHandLayout();
        refreshGameScore();
    }

    private void initializeBoardGrid() {
        GameBoard board = manager.getGameBoard();
        int defaultColor = BACKGROUND_COLOR | 0x00444444;
        final GridView boardGrid = new GridView(this, board, defaultColor, SQUARE_DIM);
        boardGrid.setId(R.id.boardView);
        if (manager.getMode() == GameMode.SHINDO)
            boardGrid.setSparkEnabled(true);
        FrameLayout boardFrame = (FrameLayout)findViewById(R.id.gridFrame);
        boardFrame.removeAllViews();
        boardFrame.addView(boardGrid);

        boardGrid.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    ClipData cd = event.getClipData();
                    Bundle bundle = cd.getItemAt(0).getIntent().getExtras();
                    Point draggingIndex = new Point(bundle.getInt("draggingIndexX"), bundle.getInt("draggingIndexY"));
                    int handPosition = bundle.getInt("handPosition");
                    GridView gv = (GridView) v;
                    Point droppingIndex = gv.getGridPositionForScreenPosition(event.getX(), event.getY());
                    Point playPosition = new Point(droppingIndex.x - draggingIndex.x, droppingIndex.y - draggingIndex.y);
                    manager.tryPlayPiece(handPosition, playPosition);
                    refreshGameViews();
                    return true;
                } else if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                return false;
            }
        });
    }

    private void initializeHandLayout() {
        List<GamePiece> hand = manager.getHand();
        LinearLayout handLayout = (LinearLayout)findViewById(R.id.handLayout);
        for (int i = 0; i < hand.size(); i++)
            handLayout.addView(createHandFrameLayout());
        refreshHandLayout();
    }

    private View createHandFrameLayout() {
        FrameLayout frame = new FrameLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        frame.setLayoutParams(params);
        return frame;
    }

    private void refreshGameScore() {
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText(Integer.toString(manager.getScore()));    // TODO: base 11?
    }

    private void refreshHandLayout() {
        for (int i = 0; i < manager.getHand().size(); i++) {
            GamePiece piece = manager.getHand().get(i);
            addPieceToHand(piece, i);
        }
    }

    private void addPieceToHand(GamePiece piece, final int handPos) {
        LinearLayout handLayout = (LinearLayout)findViewById(R.id.handLayout);
        FrameLayout pieceFrame = (FrameLayout)handLayout.getChildAt(handPos);
        pieceFrame.removeAllViews();
        if (piece != null) {
            GridView pieceView = createGridFromPiece(piece);
            pieceFrame.addView(pieceView);

            pieceView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        GridView gv = (GridView) v;
                        Point p = gv.getGridPositionForScreenPosition(event.getX(), event.getY());
                        if (p.x >= 0 && p.x < gv.getGridWidth() && p.y >= 0 && p.y < gv.getGridHeight()) {
                            View.DragShadowBuilder shadowBuilder = new GridView.GridViewShadowBuilder(v,
                                    new Point(Math.round(event.getX()), Math.round(event.getY())),
                                    manager.getMode() == GameMode.SHINDO);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt("draggingIndexX", p.x);
                            bundle.putInt("draggingIndexY", p.y);
                            bundle.putInt("handPosition", handPos);
                            intent.putExtras(bundle);
                            ClipData cd = ClipData.newIntent("gamePieceData", intent);
                            v.startDrag(cd, shadowBuilder, v, 0);
                            v.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void refreshGameViews() {
        if (!manager.checkPossiblePlays()) {
            manager.resetGame();
            initializeBoardGrid();
        }
        findViewById(R.id.boardView).invalidate();
        refreshHandLayout();
        refreshGameScore();
    }

    private GridView createGridFromPiece(GamePiece piece) {
        GridView newView = new GridView(this, piece, 0, SQUARE_DIM);
        return newView;
    }

    @Override
    public void optionSelected(GameMode mode) {
        manager.resetGame(mode);
        initializeBoardGrid();
        refreshGameViews();
    }
}
