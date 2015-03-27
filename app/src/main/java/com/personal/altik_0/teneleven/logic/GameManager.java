package com.personal.altik_0.teneleven.logic;

import android.graphics.Point;

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

    public GameManager() {
        int handSize = 3;
        r = new Random();
        hand = new ArrayList<>(handSize);
        for (int i = 0; i < handSize; i++)
            hand.add(null);
        resetGame();
    }

    public void resetGame() {
        board = new GameBoard(10, 10);
        score = 0;
        refillHand();
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
                newPiece = new SquarePiece(r.nextInt(3) + 1);
                break;
            // Line:
            case 1:
                newPiece = new LinePiece(r.nextInt(4) + 2);
                break;
            // Elbow:
            case 2:
            default:
                newPiece = new ElbowPiece(r.nextInt(2) + 2);
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
}
