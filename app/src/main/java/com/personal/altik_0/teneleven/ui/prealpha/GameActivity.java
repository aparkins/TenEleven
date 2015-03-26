package com.personal.altik_0.teneleven.ui.prealpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.personal.altik_0.teneleven.R;
import com.personal.altik_0.teneleven.logic.GameBoard;
import com.personal.altik_0.teneleven.logic.GameManager;


public class GameActivity extends Activity {
    private GameManager manager;

    private LayoutParams getGridButtonLayoutParams() {
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(2,2,2,2);
        return params;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onStart() {
        super.onStart();

        manager = new GameManager();
        TableLayout gridTable = (TableLayout)findViewById(R.id.gridTable);
        GameBoard gameBoard = manager.getGameBoard();
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            TableRow row = new TableRow(this);
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                Button gridButton = new Button(this);
                gridButton.setLayoutParams(getGridButtonLayoutParams());
                row.addView(gridButton);
            }
            gridTable.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
