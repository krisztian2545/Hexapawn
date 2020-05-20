package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BrainTest {

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
        brain = new Brain(false, false, moves);
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
    }

    @Test
    void mirrorMove() {
        assertEquals(3, brain.mirrorMove(1));
        assertEquals(2, brain.mirrorMove(2));
        assertEquals(1, brain.mirrorMove(3));
        assertEquals(6, brain.mirrorMove(4));
        assertEquals(5, brain.mirrorMove(5));
        assertEquals(4, brain.mirrorMove(6));
        assertEquals(9, brain.mirrorMove(7));
        assertEquals(8, brain.mirrorMove(8));
        assertEquals(7, brain.mirrorMove(9));
    }

    @Test
    void punish() {

    }

    @Test
    void revard() {

    }
}