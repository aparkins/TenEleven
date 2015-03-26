package com.personal.altik_0.teneleven.logic;

import android.graphics.Point;

import java.util.ArrayList;
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
        r = new Random();
        board = new GameBoard(10, 10);
        hand = new ArrayList<>(3);
        score = 0;
    }

    public GameBoard getGameBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public GamePiece generateRandomPiece() {
        switch (r.nextInt(3)) {
            // Square:
            case 0:
                return new SquarePiece(r.nextInt(3) + 1);
            // Line:
            case 1:
                return new LinePiece(r.nextInt(4) + 2);
            // Elbow:
            case 2:
            default:
                return new ElbowPiece(r.nextInt(2) + 2);
        }
    }

    public void refillHand() {
        for (int i = 0; i < hand.size(); i++) {
            hand.set(i, generateRandomPiece());
        }
    }

    public int tryPlayPiece(int handPos, Point gameGridPos) {
        if (hand.get(handPos) == null)
            return 0;
        int points = board.tryPlacePiece(hand.get(handPos), gameGridPos);
        if (points > 0) {
            score += points;
            hand.set(handPos, null);
            boolean handEmpty = false;
            for (GamePiece gp : hand)
                handEmpty |= gp == null;

            if (handEmpty)
                refillHand();

            return points;
        }
        return 0;
    }
}
