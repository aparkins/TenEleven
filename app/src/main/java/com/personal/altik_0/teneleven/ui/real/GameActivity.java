package com.personal.altik_0.teneleven.ui.real;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.altik_0.teneleven.R;
import com.personal.altik_0.teneleven.logic.ElbowPiece;
import com.personal.altik_0.teneleven.logic.GameBoard;
import com.personal.altik_0.teneleven.logic.GameManager;
import com.personal.altik_0.teneleven.logic.GamePiece;
import com.personal.altik_0.teneleven.logic.LinePiece;
import com.personal.altik_0.teneleven.ui.real.views.game.GridView;

import java.util.List;

public class GameActivity extends Activity {

    GameManager manager;

    private static final int BACKGROUND_COLOR = 0xFF333333;
    private float SQUARE_DIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        manager = new GameManager();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int minDim = Math.min(size.x, size.y);
        SQUARE_DIM = minDim * 0.06f;

        initializeHandLayout();
        initializeBoardGrid();
        refreshGameScore();
    }

    private void initializeBoardGrid() {
        GameBoard board = manager.getGameBoard();
        int defaultColor = BACKGROUND_COLOR | 0x00444444;
        GridView boardGrid = new GridView(this, board.getWidth(), board.getHeight(), defaultColor, SQUARE_DIM);
        boardGrid.setId(R.id.boardView);
        FrameLayout boardFrame = (FrameLayout)findViewById(R.id.gridFrame);
        boardFrame.addView(boardGrid);
        refreshBoardGrid();
    }

    private void refreshBoardGrid() {
        GameBoard board = manager.getGameBoard();
        GridView boardGrid = (GridView)findViewById(R.id.boardView);
        for (int x = 0; x < board.getWidth(); x++)
            for (int y = 0; y < board.getHeight(); y++)
                if (!board.getEntry(x,y))
                    boardGrid.unsetEntry(x,y);
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

    private void addPieceToHand(GamePiece piece, int handPos) {
        LinearLayout handLayout = (LinearLayout)findViewById(R.id.handLayout);
        if (piece != null) {
            FrameLayout pieceFrame = (FrameLayout)handLayout.getChildAt(handPos);
            pieceFrame.addView(createGridFromPiece(piece));

            // TODO: events
        }
    }

    private GridView createGridFromPiece(GamePiece piece) {
        GridView newView = new GridView(this, piece.getWidth(), piece.getHeight(), 0, SQUARE_DIM);
        int fillColor = getFillColorFromPiece(piece);
        for (int x = 0; x < piece.getWidth(); x++)
            for (int y = 0; y < piece.getHeight(); y++)
                if (piece.getEntry(x, y))
                    newView.setEntry(x, y, fillColor);
        return newView;
    }

    private int getFillColorFromPiece(GamePiece piece) {
        int color = 0xFF000000;
        int sizeMask = piece.getSize();
        if (piece instanceof ElbowPiece) {
            color |= 0x00cc8800;
            sizeMask |= (sizeMask & 1) << 2;
        } else if (piece instanceof LinePiece) {
            color |= 0x0000cc88;
            sizeMask <<= 1;
        } else { //piece instanceof SquarePiece
            color |= 0x008800cc;
            sizeMask |= sizeMask << 2;
            sizeMask &= 0xC;
        }

        sizeMask |= sizeMask << 4;
        sizeMask |= sizeMask << 8;
        sizeMask |= sizeMask << 16;
        color |= sizeMask;

        return color;
    }
}
