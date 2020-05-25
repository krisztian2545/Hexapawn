package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

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
        String testSate = "523409";
        List<Integer> l = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(4);
            add(8);
        }};

        for(int i = 1; i < 10; i++) {
            if(l.contains(i))
                assertTrue(GameState.isLegalMove(testSate, i));
            else
                assertFalse(GameState.isLegalMove(testSate, i));
        }

        testSate = "123789";
        l.clear();
        l.add(-2);
        l.add(-5);
        l.add(-8);

        for(int i = -1; i > -10; i--) {
            System.out.println("i = " + i);
            if(l.contains(i)) {
                System.out.println("assertTrue");
                assertTrue(GameState.isLegalMove(testSate, i));
            } else {
                System.out.println("assertFalse");
                assertFalse(GameState.isLegalMove(testSate, i));
            }
        }

    }
}