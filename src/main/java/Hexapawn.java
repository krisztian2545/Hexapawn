import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launcher class
 */
public class Hexapawn extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/launch.fxml"));
        stage.setTitle("Hexapawn");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
