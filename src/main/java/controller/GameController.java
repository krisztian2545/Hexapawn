package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Bot;
import model.Brain;
import model.GameState;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;


public class GameController {

    private GameState gameState;
    private List<Image> images;
    static Logger logger;
    private boolean canInteract;
    private int moveFrom;

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane gameGrid;

    public void initData(String username, String botname, boolean punish, boolean revard) {
        logger.info("Initializing data...");
        gameState = new GameState(username, botname, punish, revard);

        initGame();
        //gameState.gameOver();


    }

    public void initData(String username, String botname) {
        logger.info("Initializing data...");
        gameState = new GameState(username, botname);

        initGame();
    }

    public void initialize() {
        logger = LoggerFactory.getLogger(GameController.class);
        MDC.put("userId", "my user id");

        logger.info("GameController initialize...");
        images = List.of(
                new Image(getClass().getResource("/images/yellow.png").toExternalForm()),
                new Image(getClass().getResource("/images/blue.png").toExternalForm())
        );
    }

    public void initGame() {
        gameState.initGame();
        drawState(gameState.getCurrentState());
        updateState();
    }

    public void updateState() {
        if(gameState.getRound() % 2 == 1) {
            logger.info("Your turn!");
            turnLabel.setText("Your turn!");
            moveFrom = 0;
            //moveTo = 0;
            canInteract = true;

        } else {
            logger.info("Enemy's turn!");
            turnLabel.setText("Enemy's turn!");
            canInteract = false;

            gameState.moveEnemy();
            drawState(gameState.getCurrentState());
            checkGameOver();

        }
    }

    public void onGridClick(MouseEvent mouseEvent) {
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        logger.debug("Grid is pressed in: ({}, {})", row, col);

        if(canInteract) {
            if(GameState.stateToString(gameState.getCurrentState()).substring(3).contains(String.valueOf(row*3 + col+1)) ) {
                logger.debug("Clicked empty part or enemy piece.");
                return;
            }

            if(moveFrom == 0) {
                moveFrom = row*3 + col+1;
            } else {
                int moveTo = row*3 + col+1;
                int move = moveTo - moveFrom +1; // + piece index
                logger.debug("move = {}", move);

                if( (move > -1) || (move < -9) ) {
                    logger.info("Illegal move!");
                    moveFrom = 0;
                    return;
                }

                // is legal move?
                if(GameState.isLegalMove( GameState.stateToString(gameState.getCurrentState()), move )) {
                    gameState.movePlayer(move);
                    drawState(gameState.getCurrentState());
                    checkGameOver();
                } else {
                    logger.info("Illegal move!");
                    moveFrom = 0;
                }

            }
        }
    }

    public void drawState(int[] state) {
        for(int i = 0; i < 6; i++) {
            if(state[i] != 0) {
                ImageView view = (ImageView) gameGrid.getChildren().get(state[i]);
                if(i < 3)
                    view.setImage(images.get(1)); // blue
                else
                    view.setImage(images.get(0)); // yellow
            }
        }
    }

    public void checkGameOver() {
        if(gameState.isGameOver()) {
            logger.info("GAME OVER!");
            // wait for new game button
            // init new game
        } else {
            updateState();
        }
    }

}
