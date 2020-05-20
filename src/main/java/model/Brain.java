package model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Brain {

    private boolean wannaRevard;
    private boolean wannaPunish;
    private Map<String, List<Integer>> possibleMoves;
    private String lastState;
    private int lastMove;

    public Brain(boolean punish, boolean revard, Map<String, List<Integer>> moves) {
        wannaPunish = punish;
        wannaRevard = revard;
        possibleMoves = moves;
    }

    /**
     *
     * @param state
     * @return next move, represented by an integer
     * @return -1 if the given state is unknown
     */
    public int chooseMove(String state) {
        int move = 0;
        List<Integer> moves = possibleMoves.get(state);

        if(moves != null) {
            move = randomMove(moves);
        } else {
            moves = possibleMoves.get( mirrorState(state) );

            if(moves == null)
                return -1;

            move = mirrorMove( randomMove(moves) );
        }

        lastState = state;
        lastMove = move;

        return move;
    }

    /**
     * chooses a random element from the list, which will represent the next move.
     *
     * @param moves - the list of possible moves (represented in integers) in a situation
     * @return a random element (move) from the list
     * @return 0 if the given list is empty
     */
     int randomMove(List<Integer> moves) {
        if(moves.size() == 0) // when the list is empty
            return 0;

        Random r = new Random();
        return moves.get( r.nextInt(moves.size()) );
    }

     String mirrorState(String state) {
        int[] mirrored = new int[6];

        for(int i = 0; i < 6; i++) {
            mirrored[i] = mirrorMove( Character.getNumericValue(state.charAt( mirrorMove(i+1)-1 )) );
        }

        state = "";
        for(int i = 0; i < 6; i++)
            state += String.valueOf(mirrored[i]);

        return state;
    }

     int mirrorMove(int move) {

        if(move == 0)
            return 0;

        if((move % 3) == 0) {
            move -= 2;
        } else if((move % 3) == 1) {
            move += 2;
        }

        return move;
    }

    public void punish() {

    }

    public void revard() {

    }

}
