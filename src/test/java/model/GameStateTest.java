package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState("username", "testbot", true, true);
        gameState.initGame();
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

        assertFalse(GameState.isLegalMove(testSate, 0));

    }

    @Test
    void initGame() {
        GameState testGameState = new GameState("username", "testbot", true, true);
        testGameState.initGame();

        assertFalse(testGameState.isGameOver());
        assertEquals("", testGameState.getWinner());
        assertEquals(1, testGameState.getRound());
        for(int i = 0; i < 6; i++)
            assertEquals(testGameState.getSTARTING_STATE()[i], testGameState.getCurrentState()[i]);
    }

    @Test
    void getPossiblePlayerMoves() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(-2);
            add(-5);
            add(-8);
        }};
        List<Integer> possibleMoves = GameState.getPossiblePlayerMoves("123789");

        Collections.sort(l);
        Collections.sort(possibleMoves);

        System.out.println("expected list: " + l);
        System.out.println("calculated list: " + possibleMoves);

        assertEquals(l, possibleMoves);
    }

    @Test
    void normalize() {
        for(int i = 1; i < 10; i++) {
            assertEquals(((i-1) % 3)+1, GameState.normalize(i));
        }
    }

    @Test
    void stateToString() {
        String testState = "123789";
        int[] ar = new int[]{1, 2, 3, 7, 8, 9};

        assertEquals(testState, GameState.stateToString(ar));
    }

    @Test
    void moveEnemy() {
        /*gameState.movePlayer(-2); // the player moves first always
        gameState.moveEnemy();*/
    }

    @Test
    void movePlayer() {

    }

    @Test
    void checkGameState() {

    }

    @Test
    void checkGameOver() {
    }

    @Test
    void gameOver() {
    }
}