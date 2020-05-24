package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Brain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LaunchController {

    static Logger logger;

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

        logger = LoggerFactory.getLogger(LaunchController.class);
        MDC.put("userId", "my user id");

        enemy_list.setItems(getEnemyNames());
        enemy_list.getSelectionModel().select(0);

    }

    @FXML
    public void startGame(ActionEvent event) throws IOException {
        if (player_name.getText().isEmpty()) {
            errorLabel.setText("* Username is empty!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();

        RadioButton rb = (RadioButton) enemy.getSelectedToggle();
        logger.debug("Selectec radio button's text: {}", rb.getText());
        if(rb.getText().equals("Create New:")) {
            if (new_enemy_name.getText().isEmpty()) {
                errorLabel.setText("* Enemy name is empty!");
                return;
            }
            logger.info("Choosen option: create new enemy...");

            fxmlLoader.<GameController>getController().initData(player_name.getText(), new_enemy_name.getText(), punish.isSelected(), revard.isSelected());
            addFileName(new_enemy_name.getText());
        } else {

            logger.info("Choosen option: use existing enemy...");
            fxmlLoader.<GameController>getController().initData(player_name.getText(), (String )enemy_list.getValue());
        }


        //if(enemy.)

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        //log.info("Username is set to {}, loading game scene.", usernameTextfield.getText());
    }

    private ObservableList getEnemyNames() {
        ObservableList out = FXCollections.observableArrayList();
        File folder = new File("brains/");
        if(!folder.exists()) {
            logger.info("Creating folder '{}' ...", folder.toPath() );
            logger.debug("Succes: {}", folder.mkdir());
        }

        String[] fileNames = folder.list();
        logger.debug("Files in brains: {}", fileNames);

        for(String s : fileNames) {
            logger.debug("File name: {}", s);
            if(s.contains(".json")) {
                out.add(s.replace(".json", ""));
            }
        }

        /*InputStream is = getClass().getResourceAsStream("/brains/file_names.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> names;

        try {
            String line = buf.readLine();
            names = new ArrayList<>();

            while(line != null){
                names.add(line);
                line = buf.readLine();
            }

            out.addAll(names);
            //out = FXCollections.observableArrayList(names);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }*/

        return out;
    }

    private void addFileName(String name) {

    }

}
