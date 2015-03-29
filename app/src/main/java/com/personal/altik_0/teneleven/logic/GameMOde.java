package com.personal.altik_0.teneleven.logic;

/**
 * Created by Altik_0 on 3/29/2015.
 */
public enum GameMode {
    NORMAL("And now back to your regularly scheduled programming.", "Normal Game"),
    SAI("But how can I play if I can't touch the stones?!", "Sai"),
    SHINDO("I want to place a stone like THAT!", "Hikaru Shindo"),
    AKIRA("That isn't the best move... it's not even a GOOD move!", "Akira Toya"),
    KURATA("Oh no... I've gone and lost track of the stones!", "Kurata 7 Dan");

    private String description;
    private String name;
    GameMode(String _description, String _name) {
        description = _description;
        name = _name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
