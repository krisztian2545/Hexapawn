package controller;

import javafx.event.ActionEvent;
import model.Bot;
import model.Brain;
import model.GameState;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


public class GameController {

    private GameState gameState;
    static Logger logger;

    public void initData(String username, String botname, boolean punish, boolean revard) {
        gameState = new GameState(username, botname, punish, revard);

        logger.info("Initializing data...");
        gameState.gameOver();
    }

    public void initData(String username, String botname) {
        gameState = new GameState(username, botname);

        logger.info("Initializing data...");
    }

    public void initialize() {
        logger = LoggerFactory.getLogger(Brain.class);
        MDC.put("userId", "my user id");

        logger.info("GameController initialize...");
    }

    public void drawState(String state) {

    }

}
