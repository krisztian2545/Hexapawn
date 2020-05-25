package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BrainTest {

    Brain brain;

    @BeforeEach
    void setUp() {
        Map<String, List<Integer>> moves = new HashMap<>();

        moves.put("123759", new ArrayList<Integer>() {{
            add(2);
            add(3);
        }});
        moves.put("123489", new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }});
        moves.put("423756", new ArrayList<Integer>() {{
            add(6);
            add(6);
            add(8);
        }});


        brain = new Brain(true, true, moves);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void chooseMove() {
        /*System.out.println("move: " + brain.chooseMove("123489"));
        System.out.println("move: " + brain.chooseMove("123489"));
        System.out.println("move: " + brain.chooseMove("123489"));
        System.out.println("move: " + brain.chooseMove("123489"));*/

        List<Integer> l = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }};

        for(int i = 0; i < 10; i++)
            assertTrue(l.contains(brain.chooseMove("123489")));

        /*l.clear();
        l.add(5);
        l.add(6);
        l.add(2);

        for(int i = 0; i < 10; i++) {
            int m = brain.chooseMove("123786");
            assertTrue(l.contains(m));
        }*/

    }

    @Test
    void randomMove() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }};

        for(int i = 0; i < 10; i++)
            assertTrue(l.contains(brain.randomMove(l) ));
    }

    @Test
    void mirrorState() {
        String result;

        result = brain.mirrorState("423789");
        System.out.println(result);
        assertEquals("126789", result);

        result = brain.mirrorState("143059");
        System.out.println(result);
        assertEquals("163750", result);

        result = brain.mirrorState("520400");
        System.out.println(result);
        assertEquals("025006", result);
    }

    @Test
    void mirrorPos() {
        assertEquals(3, brain.mirrorPos(1));
        assertEquals(2, brain.mirrorPos(2));
        assertEquals(1, brain.mirrorPos(3));
        assertEquals(6, brain.mirrorPos(4));
        assertEquals(5, brain.mirrorPos(5));
        assertEquals(4, brain.mirrorPos(6));
        assertEquals(9, brain.mirrorPos(7));
        assertEquals(8, brain.mirrorPos(8));
        assertEquals(7, brain.mirrorPos(9));
    }

    @Test
    void mirrorMove() {
        assertEquals(9, brain.mirrorMove(1));
        assertEquals(8, brain.mirrorMove(2));
        assertEquals(7, brain.mirrorMove(3));
        assertEquals(6, brain.mirrorMove(4));
        assertEquals(5, brain.mirrorMove(5));
        assertEquals(4, brain.mirrorMove(6));
        assertEquals(3, brain.mirrorMove(7));
        assertEquals(2, brain.mirrorMove(8));
        assertEquals(1, brain.mirrorMove(9));
    }

    @Test
    void punish() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(3);
        }};
        int badMove = 2;
        brain.setLastState("123759");
        brain.setLastMove(badMove);
        brain.punish();
        System.out.println("punished list: " + brain.getPossibleMoves().get("123759"));
        assertEquals(l, brain.getPossibleMoves().get("123759"));

        l.clear();
        l.add(6);
        l.add(8);
        badMove = 6;
        brain.setLastState("423756");
        brain.setLastMove(badMove);
        brain.punish();
        System.out.println("punished list: " + brain.getPossibleMoves().get("423756"));
        assertEquals(l, brain.getPossibleMoves().get("423756"));
    }

    @Test
    void revard() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(3);
            add(2);
            add(3);
        }};
        int goodMove = 3;
        brain.setLastState("123759");
        brain.setLastMove(goodMove);
        brain.revard();
        List<Integer> revarded = brain.getPossibleMoves().get("123759");
        Collections.sort(revarded);
        Collections.sort(l);
        System.out.println("revarded list: " + revarded);
        assertEquals(l, revarded);
    }

    @Test
    void learnState() {
        List<Integer> l = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(4);
            add(8);
        }};
        String newState = "523409";

        assertTrue(!brain.getPossibleMoves().containsKey(newState));
        brain.process(newState);
        assertTrue(brain.getPossibleMoves().containsKey(newState));
        assertEquals(l, brain.getPossibleMoves().get(newState));
    }
}