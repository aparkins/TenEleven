package com.personal.altik_0.teneleven.logic;

import android.graphics.Point;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Altik_0 on 3/25/2015.
 */
public class GameManager {
    private Random r;
    private GameBoard board;
    private ArrayList<GamePiece> hand;
    private int score;
    private GameMode mode;

    public GameManager(GameMode _mode) {
        int handSize = 3;
        r = new Random();
        hand = new ArrayList<>(handSize);
        for (int i = 0; i < handSize; i++)
            hand.add(null);
        mode = _mode;
        resetGame();
    }

    public GameManager(Bundle saveData) {
        score = saveData.getInt("score");
        mode = (GameMode)saveData.getSerializable("gameMode");
        int handSize = saveData.getInt("handSize");
        hand = new ArrayList<>(handSize);
        r = new Random();
        for (int i = 0; i < handSize; i++) {
            int[][] pieceGrid = (int[][])saveData.getSerializable("piece" + i);
            if (pieceGrid != null)
                hand.add(new ReloadedPiece(pieceGrid, mode));
            else
                hand.add(null);
        }
        int[][] boardGrid = (int[][])saveData.getSerializable("gameGrid");
        board = new GameBoard(boardGrid, mode);
    }

    public void resetGame() {
        board = new GameBoard(10, 10, mode);
        score = 0;
        refillHand();
    }

    public void resetGame(GameMode _mode) {
        mode = _mode;
        resetGame();
    }

    public GameBoard getGameBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public List<GamePiece> getHand() {
        return hand;
    }

    public GamePiece generateRandomPiece() {
        GamePiece newPiece;
        switch (r.nextInt(3)) {
            // Square:
            case 0:
                newPiece = new SquarePiece(r.nextInt(3) + 1, mode);
                break;
            // Line:
            case 1:
                newPiece = new LinePiece(r.nextInt(4) + 2, mode);
                break;
            // Elbow:
            case 2:
            default:
                newPiece = new ElbowPiece(r.nextInt(2) + 2, mode);
                break;
        }
        newPiece.rotatePiece(r.nextInt(4));
        return newPiece;
    }

    public void refillHand() {
        for (int i = 0; i < hand.size(); i++) {
            hand.set(i, generateRandomPiece());
        }
    }

    public boolean checkPossiblePlays() {
        boolean canPlay = false;
        for (GamePiece piece : hand) {
            if (piece != null) {
                canPlay |= board.canPlacePiece(piece);
            }
        }
        return canPlay;
    }

    public int tryPlayPiece(int handPos, Point gameGridPos) {
        if (hand.get(handPos) == null)
            return 0;

        if (mode == GameMode.AKIRA)
            gameGridPos = new Point(gameGridPos.y, gameGridPos.x);

        int points = board.tryPlacePiece(hand.get(handPos), gameGridPos);
        if (points > 0) {
            score += points;
            hand.set(handPos, null);
            boolean handEmpty = true;
            for (GamePiece gp : hand)
                handEmpty &= gp == null;

            if (handEmpty)
                refillHand();

            return points;
        }
        return 0;
    }

    public Bundle saveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putInt("handSize", hand.size());
        bundle.putSerializable("gameMode", mode);
        bundle.putSerializable("gameGrid", board.getGrid());
        for (int i = 0; i < hand.size(); i++) {
            GamePiece piece = hand.get(i);
            if (piece != null) {
                bundle.putSerializable("piece" + i, hand.get(i).getGrid());
            } else {
                bundle.putSerializable("piece" + i, null);
            }

        }
        return bundle;
    }
}
