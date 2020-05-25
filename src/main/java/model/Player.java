package model;

import lombok.Data;

/**
 * The player class,
 * goes in odd number of rounds
 */
@Data
public class Player {

    /**
     * the name of the player
     */
    private String name;

    /**
     * the number of wins
     */
    private int wins;

    /**
     * sets the {@code wins} field to 0 and the player's {@code name} to the given value
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        wins = 0;
    }

    /**
     * increases the {@code wins}' value by 1
     */
    public void won() {
        wins++;
    }

}
