package model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The Brain class.
 *
 */
@Data
public class Brain {

    /**
     * if {@code true} then the last move that led to victory will be revarded
     */
    private boolean wannaRevard;

    /**
     * if {@code true} then the last move that led to failure will be punished
     */
    private boolean wannaPunish;

    /**
     * stores the possible moves for the states, where the state is the key in a form of 6 character long {@code String} where the first 3 number is the location of the Bot's pieces and the last 3 is the location of the player's pieces. The location is a number from 1 to 9 from the top left corner to the bottom right.
     */
    private Map<String, List<Integer>> possibleMoves;

    /**
     * stores the last state that has been processed
     */
    private String lastState;

    /**
     * stores the last move that has been made by the Bot
     */
    private int lastMove;

    private static Logger logger;

    /**
     * initializes the object with the given parameters
     * @param punish - set to {@code true} if you want to avoid the same mistakes again and again
     * @param revard - set to {@code true} if you want increase the chance of a move that led to victory
     * @param moves - the {@code Map<String, List<Integer>>} that contains the possible moves for the states
     */
    public Brain(boolean punish, boolean revard, Map<String, List<Integer>> moves) {
        wannaPunish = punish;
        wannaRevard = revard;
        possibleMoves = moves;

        initLogger();
    }

    /**
     * initializes the logger to prevent the @exception NullPointerException when the Brain is loaded from a json file
     */
    public void initLogger() {
        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");
    }

    /**
     * asks for a possible move for the given state. When encountering a new state, calls the {@link model.Brain#learnState(String)} method and then chooses a move.
     * @param state - the state to process
     * @return the choosen move
     */
    public int process(String state) {
        int nextMove = chooseMove(state);

        if(nextMove == 0) {
            logger.debug("Improvising...");

            learnState(state);
            nextMove = chooseMove(state);
        }

        return nextMove;
    }

    /**
     * learns the given state by putting all the currently possible moves to the {@link model.Brain#possibleMoves} where the key is the given {@code state}
     * @param state - a new state
     */
    public void learnState(String state) {
        List<Integer> legalMoves = GameState.getPossibleBotMoves(state);
        possibleMoves.put(state, legalMoves);

        logger.debug("New state learned: {} => {}", state, legalMoves);
    }

    /**
     * gives a move for the given state.
     * @param state - the state to choose a move for
     * @return next move, represented by an integer, {@code 0} if the given state is unknown
     */
    public int chooseMove(String state) {
        List<Integer> moves = possibleMoves.get(state);

        if(moves == null)
            return 0;

        int move = randomMove(moves);
        lastState = state;
        lastMove = move;

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

    /**
     * removes the value of {@link model.Brain#lastMove} from the list stored in the {@link model.Brain#possibleMoves} with {@link model.Brain#lastState} key.
     */
    public void punish() {
        if(wannaPunish && !possibleMoves.isEmpty()){
            List<Integer> modified = possibleMoves.get(lastState);
            logger.debug("before modification: " + modified.toString());

            if(modified.size() > 1)
                modified.remove((Integer) lastMove);

            possibleMoves.replace(lastState, modified);
        }

    }

    /**
     * adds one more of the value of {@link model.Brain#lastMove} to the list stored in the {@link model.Brain#possibleMoves} with {@link model.Brain#lastState} key.
     */
    public void revard() {
         if(wannaRevard && !possibleMoves.isEmpty()) {
             List<Integer> modified = possibleMoves.get(lastState);
             logger.debug("before modification: {}", modified.toString());

             if(modified.size() > 1)
                 modified.add(lastMove);

             possibleMoves.replace(lastState, modified);
         }
    }

}
