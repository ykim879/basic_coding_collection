package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GetStartedController {
    @FXML
    private ImageView nxtBtn;
    @FXML
    private void clickNextBtn() throws IOException {
        Scene scene = nxtBtn.getScene();
        Stage stage = (Stage) scene.getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("initial_configuration.fxml"));
        // create a pane contain the view
        scene.setRoot(root);
    }
}
