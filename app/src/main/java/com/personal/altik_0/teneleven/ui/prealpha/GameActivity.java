package com.personal.altik_0.teneleven.ui.prealpha;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.personal.altik_0.teneleven.R;
import com.personal.altik_0.teneleven.logic.GameBoard;
import com.personal.altik_0.teneleven.logic.GameManager;
import com.personal.altik_0.teneleven.logic.GamePiece;


public class GameActivity extends Activity {
    private GameManager manager;
    private Integer selectedHandPos = null;

    private LayoutParams getGridButtonLayoutParams() {
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(2,2,2,2);
        return params;
    }

    private Button.OnClickListener getGridOnClickListener(final int x, final int y) {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedHandPos == null)
                    return;

                int result = manager.tryPlayPiece(selectedHandPos, new Point(x, y));
                if (result > 0) {
                    if (!manager.checkPossiblePlays())
                        manager.resetGame();

                    clearHandSelection();
                    refreshHandView();
                    updateScoreDisplay();
                    updateGameBoard();
                }
            }
        };
    }

    private void clearHandSelection() {
        selectedHandPos = null;
        findViewById(R.id.pieceGridTable1).setBackgroundColor(0);
        findViewById(R.id.pieceGridTable2).setBackgroundColor(0);
        findViewById(R.id.pieceGridTable3).setBackgroundColor(0);
    }

    private Button.OnClickListener getHandOnClickListener(final int i) {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHandSelection();
                selectedHandPos = i;
                ((View)v.getParent().getParent()).setBackgroundColor(0xFFFF0000);
            }
        };
    }

    private void updateGameBoard() {
        GameBoard board = manager.getGameBoard();
        TableLayout gridTable = (TableLayout)findViewById(R.id.gridTable);
        for (int x = 0; x < board.getWidth(); x++) {
            TableRow row = (TableRow)gridTable.getChildAt(x);
            for (int y = 0; y < board.getHeight(); y++) {
                View gridEntry = row.getChildAt(y);
                if (board.getEntry(x, y) != 0) {
                    gridEntry.getBackground().setColorFilter(board.getEntry(x, y), PorterDuff.Mode.MULTIPLY);
                } else {
                    gridEntry.getBackground().setColorFilter(null);
                }
            }
        }
    }

    private void updateScoreDisplay() {
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText(Integer.toString(manager.getScore(), 11).toUpperCase());
    }

    private void initHandGridView(int viewId, int handPos) {
        TableLayout pieceGridTable = (TableLayout)findViewById(viewId);
        pieceGridTable.removeAllViews();
        GamePiece piece = manager.getHand().get(handPos);
        if (piece != null) {
            for (int x = 0; x < piece.getWidth(); x++) {
                TableRow row = new TableRow(this);
                for (int y = 0; y < piece.getHeight(); y++) {
                    View gridEntry;
                    if (piece.getEntry(x, y) != 0) {
                        gridEntry = new Button(this);
                        gridEntry.setOnClickListener(getHandOnClickListener(handPos));
                    } else {
                        gridEntry = new TextView(this);
                    }
                    gridEntry.setLayoutParams(getGridButtonLayoutParams());
                    row.addView(gridEntry);
                }
                pieceGridTable.addView(row);
            }
        }
    }

    private void refreshHandView() {
        initHandGridView(R.id.pieceGridTable1, 0);
        initHandGridView(R.id.pieceGridTable2, 1);
        initHandGridView(R.id.pieceGridTable3, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_prealpha);

        manager = new GameManager();
        // Score
        updateScoreDisplay();
        // Game Grid
        TableLayout gridTable = (TableLayout)findViewById(R.id.gridTable);
        GameBoard gameBoard = manager.getGameBoard();
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            TableRow row = new TableRow(this);
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                Button gridButton = new Button(this);
                gridButton.setLayoutParams(getGridButtonLayoutParams());
                gridButton.setOnClickListener(getGridOnClickListener(x, y));
                row.addView(gridButton);
            }
            gridTable.addView(row);
        }
        // Hand
        refreshHandView();
    }
}
