package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPossibleBotMoves() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }};
        List<Integer> possibleMoves = GameState.getPossibleBotMoves("123489");

        Collections.sort(l);
        Collections.sort(possibleMoves);

        System.out.println("expected list: " + l);
        System.out.println("calculated list: " + possibleMoves);

        assertEquals(l, possibleMoves);
    }

    @Test
    void isLegalMove() {

    }
}