package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameState {

    private Player human;
    private Bot enemy;
    private int[] currentState;

    private final int[] STARTING_STATE = new int[]{1, 2, 3, 7, 8, 9};

    public GameState() {

        currentState = STARTING_STATE;

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
        if(move < 0) {
            move *= -1;

            int c = ((move-1) / 3);
            int pos = Character.getNumericValue(state.charAt(3+c));
            if(pos == 0)
                return false;

            int x = (4 - normalize(pos)) + (4 - normalize(move))+1;
            int newPos = pos - (4 - normalize(move))+1;

            if((x < 4) || (x > 6))
                return false;

            if( state.contains(String.valueOf(newPos)) && (normalize(move) == 2) )
                return false;

            if( !state.substring(0, 2).contains(String.valueOf(newPos)) && (normalize(move) != 2) )
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

}