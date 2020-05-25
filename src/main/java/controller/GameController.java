package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Bot;
import model.Brain;
import model.GameState;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
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
    private Label playerName;

    @FXML
    private Label botName;

    @FXML
    private GridPane gameGrid;

    public void initData(String username, String botname, boolean punish, boolean revard) {
        logger.info("Initializing data...");
        gameState = new GameState(username, botname, punish, revard);

        //initGame();
        //gameState.gameOver();


    }

    public void initData(String username, String botname) {
        logger.info("Initializing data...");
        gameState = new GameState(username, botname);

        //initGame();
    }

    public void initialize() {
        logger = LoggerFactory.getLogger(GameController.class);
        MDC.put("userId", "my user id");

        logger.info("GameController initialize...");
        images = List.of(
                new Image(getClass().getResource("/images/yellow.png").toExternalForm()),
                new Image(getClass().getResource("/images/blue.png").toExternalForm()),
                new Image(getClass().getResource("/images/blank.png").toExternalForm())
        );
    }

    public void initGame() {
        logger.info("Initializing new game...");
        gameState.initGame();
        updateWins();
        logger.debug("Starting with state: {}", GameState.stateToString(gameState.getCurrentState()) );
        drawState(gameState.getCurrentState());
        updateState();
    }

    public void updateState() {
        logger.info("Round number {}", gameState.getRound());
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

            if(moveFrom == 0) {
                if(!GameState.stateToString(gameState.getCurrentState()).substring(3).contains(String.valueOf(row*3 + col+1)) ) {
                    logger.debug("Clicked empty part or enemy piece.");
                    return;
                }

                moveFrom = row*3 + col+1;
                logger.debug("Move from: {}", moveFrom);
            } else {
                int moveTo = row*3 + col+1;
                int normMove = moveTo - moveFrom +1;
                int selectedPiece = GameState.stateToString(gameState.getCurrentState()).substring(3).indexOf(String.valueOf(moveFrom));
                int move = normMove - (selectedPiece * 3);
                logger.debug("Move to: {}", moveTo);
                logger.debug("move = {}", move);

                if( (normMove > -1) || (normMove < -3) ) {
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
        for(int i = 0; i < 9; i++) {
            ImageView view = (ImageView) gameGrid.getChildren().get(i);
            view.setImage(images.get(2));
        }

        for(int i = 0; i < 6; i++) {
            if(state[i] != 0) {
                ImageView view = (ImageView) gameGrid.getChildren().get(state[i]-1);
//                if (view.getImage() != null) {
//                    logger.debug("Image({}) = {}", i, view.getImage().getUrl());
//                }
                if(i < 3)
                    view.setImage(images.get(1)); // blue
                else
                    view.setImage(images.get(0)); // yellow
            }
        }
    }

    public void checkGameOver() {
        if(gameState.isGameOver()) {
            logger.info("GAME OVER!\nThe winner is: {}", gameState.getWinner());
            turnLabel.setText("The winner is: " + gameState.getWinner());
            canInteract = false;
            updateWins();
            // wait for new game button
            // init new game
        } else {
            updateState();
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        gameState.getEnemy().exportBrain();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void restart(ActionEvent event) {
        logger.info("New game button pressed.");
        initGame();
    }

    private void updateWins() {
        String playerWinText = gameState.getHuman().getName() + "\nWins:\n";
        for(int i = 0; i < gameState.getHuman().getWins(); i++)
            playerWinText += "|";
        playerName.setText(playerWinText);

        String enemyWinText = gameState.getEnemy().getName() + "\nWins:\n";
        for(int i = 0; i < gameState.getEnemy().getWins(); i++)
            enemyWinText += "|";
        botName.setText(enemyWinText);
    }

}
