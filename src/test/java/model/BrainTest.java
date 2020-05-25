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

        List<Integer> l = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(8);
        }};

        for(int i = 0; i < 10; i++)
            assertTrue(l.contains(brain.chooseMove("123489")));

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