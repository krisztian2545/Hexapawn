package model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The Brain class
 */
@Data
public class Brain {

    private boolean wannaRevard;
    private boolean wannaPunish;
    private Map<String, List<Integer>> possibleMoves;
    private String lastState;
    private int lastMove;

    private static Logger logger;

    public Brain(boolean punish, boolean revard, Map<String, List<Integer>> moves) {
        wannaPunish = punish;
        wannaRevard = revard;
        possibleMoves = moves;

        initLogger();
    }

    public void initLogger() {
        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");
    }

    public int process(String state) {
        int nextMove = chooseMove(state);

        if(nextMove == 0) {
            logger.debug("Improvising...");

            learnState(state);
            nextMove = chooseMove(state);
        }

        return nextMove;
    }

    public void learnState(String state) {
        List<Integer> legalMoves = GameState.getPossibleBotMoves(state);
        possibleMoves.put(state, legalMoves);

        logger.debug("New state learned: {} => {}", state, legalMoves);
    }

    /**
     *
     * @param state -
     * @return next move, represented by an integer; -1 if the given state is unknown
     */
    public int chooseMove(String state) {
        int move = 0;
        List<Integer> moves = possibleMoves.get(state);

        if(moves != null) {
            move = randomMove(moves);
            lastState = state;
            lastMove = move;
        } else {
            moves = possibleMoves.get( mirrorState(state) );

            if(moves == null)
                return 0;

            move = mirrorMove( randomMove(moves) );
            lastState = state;
            lastMove = mirrorMove(move);
        }

        return move;
    }

    /**
     * chooses a random element from the list, which will represent the next move.
     *
     * @param moves - the list of possible moves (represented in integers) in a situation
     * @return a random element (move) from the list; 0 if the given list is empty
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
            mirrored[i] = mirrorPos( Character.getNumericValue(state.charAt( mirrorPos(i+1)-1 )) );
        }

        state = "";
        for(int i = 0; i < 6; i++)
            state += String.valueOf(mirrored[i]);

        return state;
    }

     int mirrorPos(int pos) {

        if(pos == 0)
            return 0;

        if((pos % 3) == 0) {
            pos -= 2;
        } else if((pos % 3) == 1) {
            pos += 2;
        }

        return pos;
    }

    int mirrorMove(int move) {
         /*move = mirrorPos(move);

         if(move > 6) {
             move -= 6;
         } else if(move < 4) {
             move += 6;
         }*/

         return 10 - move;
    }

    public void punish() {
        //should remove revarded moves all at once?
        //logger.debug("Before punisment:\nLast state: {}\nLast move\nMoves: {}", lastState, lastMove, possibleMoves);
        if(wannaPunish && !possibleMoves.isEmpty()){
            List<Integer> modified = possibleMoves.get(lastState);
            logger.debug("before modification: " + modified.toString());

            if(modified.size() > 1)
                modified.remove((Integer) lastMove);

            possibleMoves.replace(lastState, modified);
        }

    }

    public void revard() {
         if(wannaRevard && !possibleMoves.isEmpty()) {
             List<Integer> modified = possibleMoves.get(lastState);
             // only add if it contains more than 1 type of move
             logger.debug("before modification: " + modified.toString());

             if(modified.size() > 1)
                 modified.add(lastMove);

             possibleMoves.replace(lastState, modified);
         }
    }

}
