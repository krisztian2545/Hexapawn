package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LaunchController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField player_name;

    @FXML
    private TextField new_enemy_name;

    @FXML
    private ToggleGroup enemy;

    @FXML
    private CheckBox punish;

    @FXML
    private CheckBox revard;

    @FXML
    private ChoiceBox enemy_list;


    @FXML
    public void initialize() {


    }

    @FXML
    public void startGame(ActionEvent event) throws IOException {
        if (player_name.getText().isEmpty()) {
            errorLabel.setText("* Username is empty!");
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        if(enemy.)
        fxmlLoader.<GameController>getController().initData(player_name.getText(), );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Username is set to {}, loading game scene.", usernameTextfield.getText());
    }

    private ObservableList getEnemyNames() {
        ObservableList out;
        // File.list();

        return out;
    }

}
