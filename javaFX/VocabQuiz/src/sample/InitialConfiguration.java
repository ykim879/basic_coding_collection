package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Chapters;
import sample.Model.Level;

import java.io.IOException;

public class InitialConfiguration {
    @FXML
    private Label count;
    @FXML
    private ComboBox<Level> levels;
    private ObservableList<Level> levelOptions;
    @FXML
    private Label warning1, warning2, warning3;
    private int wordCount;
    private int leftWord;
    @FXML
    private TextField chapterOne, chapterTwo, chapterThree;
    private static Chapters chapter;
    private int result1,result2,result3;
    @FXML
    private Button submit;
    @FXML
    public void initialize() {
        levelOptions = FXCollections.observableArrayList();
        for(Level l : Level.values()) {
            levelOptions.addAll(l);
        }
        levels.getItems().addAll(levelOptions); // when you click you can see the options now.
        //also when you click you can have the message
        levels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Level>() {// ChangeLister interface
            @Override
            public void changed(ObservableValue<? extends Level> observable, Level oldValue, Level newValue) {
                if(newValue == Level.EASY) {
                    wordCount = 10;
                    count.setText("10");
                } else if(newValue == Level.MED) {
                    wordCount = 20;
                    count.setText("20");
                } else {
                    wordCount = 30;
                    count.setText("30");
                }
            }
        });
    }
    @FXML
    public void getChapterOne() {
        try {
            result1 = Integer.parseInt(chapterOne.getText());
            leftWord = wordCount - (result1 + result3 + result2);
            count.setText(leftWord + "");
            if (result1 > wordCount || result1 < 0 || leftWord < 0) {
                warning1.setText("The value is invalid");
            } else {
                warning1.setText("ready!");
            }
        } catch (NumberFormatException e1) {
            warning1.setText("The value is not a digit");
        }
    }
    @FXML
    public void getChapterTwo() {
        try {
            result2 = Integer.parseInt(chapterTwo.getText());
            leftWord = wordCount - (result1 + result3 + result2);
            count.setText(leftWord + "");
            if (result2 > wordCount || result2 < 0 || leftWord < 0) {
                warning2.setText("The value is invalid");
            } else {
                warning2.setText("ready!");
            }
        } catch (NumberFormatException e1) {
            warning2.setText("The value is not a digit");
        }
    }
    @FXML
    public void getChapterThree() {
        try {
            result3 = Integer.parseInt(chapterThree.getText());
            leftWord = wordCount - (result1 + result3 + result2);
            count.setText(leftWord + "");
            if (result3 > wordCount || result3 < 0 || leftWord < 0) {
                warning3.setText("The value is invalid");
            } else {
                warning3.setText("ready!");
            }
        } catch (NumberFormatException e1) {
            warning3.setText("The value is not a digit");
        }
    }
    @FXML
    private void submitPressed() throws IOException {
        /*if(){

        }*/
        chapter = new Chapters(result1, result2, result3);
        // setup and display next scene
        Scene scene = submit.getScene(); // get Scene from component
        Stage stage = (Stage) scene.getWindow(); // get the Stage from Scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("get_started.fxml"));
        Parent root = (Parent) loader.load();  // create a pane contain the view
        scene.setRoot(root); // show the view
    }
    public static Chapters getChapter() {
        return chapter;
    }
}
