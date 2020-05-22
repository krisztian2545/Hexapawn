package model;

import lombok.Data;

/**
 * The player
 */
@Data
public class Player {

    private String name;
    private int wins;

    public Player(String name) {
        this.name = name;
        wins = 0;
    }

}
