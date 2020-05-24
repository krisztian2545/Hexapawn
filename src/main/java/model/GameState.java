package model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameState {

    private Player human;
    private Bot enemy;
    private int[] currentState;
    private int round;
    private boolean gameOver;
    private String winner;

    static Logger logger = LoggerFactory.getLogger(GameState.class);
        //MDC.put("userId", "my user id");

    private final int[] STARTING_STATE = new int[]{1, 2, 3, 7, 8, 9};

    public GameState(String username, String botname, boolean punish, boolean revard) {
        logger = LoggerFactory.getLogger(GameState.class);
        MDC.put("userId", "my user id");

        human = new Player(username);
        enemy = new Bot(botname, punish, revard);

        //initGame();

    }

    public GameState(String username, String botname) {
        logger = LoggerFactory.getLogger(GameState.class);
        MDC.put("userId", "my user id");

        human = new Player(username);
        enemy = new Bot(botname);

        //initGame();

    }

    public void initGame() {
        gameOver = false;
        winner = "";
        round = 1;
        currentState = STARTING_STATE;
    }

    public static List<Integer> getPossiblePlayerMoves(String state) {
        List<Integer> legalMoves = new ArrayList<>();
        for(int i = -1; i > -10; i--) {
            if(isLegalMove(state, i))
                legalMoves.add(i);
        }

        return legalMoves;
    }

    public static List<Integer> getPossibleBotMoves(String state) {
        List<Integer> legalMoves = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            if(isLegalMove(state, i))
                legalMoves.add(i);
        }

        return legalMoves;
    }

    public static boolean isLegalMove(String state, int move) {
        logger.debug("Checking if it is a legal move: " + move);

        if(move == 0)
            return false;

        if(move < 0) {
            move *= -1;
            logger.debug("move = {}", move);

            int c = ((move-1) / 3);
            int pos = Character.getNumericValue(state.charAt(3+c));
            logger.debug("c = {}", c);
            logger.debug("pos = {}", pos);
            if(pos == 0)
                return false;

            int x = (4 - normalize(pos)) + /*(4 - */normalize(move)/*)*/+1;
            int newPos = pos - (normalize(move)+1);

            logger.debug("x = {}", x);
            logger.debug("newPos = {}", newPos);

            logger.debug("(x < 4) || (x > 6) = {}", (x < 4) || (x > 6));
            if((x < 4) || (x > 6))
                return false;

            logger.debug("state.contains(String.valueOf(newPos)) && (normalize(move) == 2) = {}", state.contains(String.valueOf(newPos)) && (normalize(move) == 2));
            if( state.contains(String.valueOf(newPos)) && (normalize(move) == 2) )
                return false;

            logger.debug("!state.substring(0, 3).contains(String.valueOf(newPos)) && (normalize(move) != 2) = {}", !state.substring(0, 3).contains(String.valueOf(newPos)) && (normalize(move) != 2));
            if( !state.substring(0, 3).contains(String.valueOf(newPos)) && (normalize(move) != 2) )
                return false;

        } else {

            int c = ((move-1) / 3);
            int pos = Character.getNumericValue(state.charAt(c));
            if(pos == 0)
                return false;

            int x = normalize(pos) + normalize(move)+1;
            int newPos = pos + normalize(move)+1;

            if((x < 4) || (x > 6))
                return false;

            if( state.contains(String.valueOf(newPos)) && (normalize(move) == 2) )
                return false;

            if( !state.substring(3).contains(String.valueOf(newPos)) && (normalize(move) != 2) )
                return false;

        }

        return true;
    }

    public static int normalize(int x) {
        return x + (((x-1) / 3) * -3);
    }

    public static String stateToString(int[] state) {
        String newstate = "";
        for(int i = 0; i < 6; i++)
            newstate += String.valueOf(state[i]);

        return newstate;
    }

    public void moveEnemy() {
        int move = enemy.getMove(stateToString(currentState));

        int pieceIndex = (move-1) / 3;
        currentState[pieceIndex] += normalize(move)+1;

        for(int i = 3; i < 6; i++) {
            if(currentState[i] == currentState[pieceIndex]) {
                currentState[i] = 0;
            }
        }

        checkGameOver();
    }

    public void movePlayer(int move) {



        checkGameOver();
    }

    public void checkGameState() {

        if(round % 2 == 1) {
            // no more enemy
            if(stateToString(currentState).substring(0, 3).equals("000") ) {
                gameOver = true;
                winner = "Player";
                return;
            }

            // the enemy can't move
            if(getPossibleBotMoves(stateToString(currentState)).size() == 0) {
                gameOver = true;
                winner = "Player";
                return;
            }

            // reached the end of table
            if((currentState[3] < 4) || (currentState[4] < 4) || (currentState[5] < 4)) {
                gameOver = true;
                winner = "Player";
                return;
            }

        } else {
            // no more player pieces
            if(stateToString(currentState).substring(3).equals("000") ) {
                gameOver = true;
                winner = "Enemy";
                return;
            }

            // the player can't move
            if(getPossiblePlayerMoves(stateToString(currentState)).size() == 0) {
                gameOver = true;
                winner = "Enemy";
                return;
            }

            // reached the end of table
            if((currentState[0] > 6) || (currentState[1] > 6) || (currentState[2] > 6)) {
                gameOver = true;
                winner = "Enemy";
                return;
            }
        }

    }

    public void checkGameOver() {
        checkGameState();
        if(gameOver) {
            gameOver();
        } else {
            round++;
        }
    }

    public void gameOver() {
        enemy.feedback(winner.equals("Enemy"));
    }

}
