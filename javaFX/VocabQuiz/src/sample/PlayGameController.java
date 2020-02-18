package sample;


import sample.Model.Chapters;

public class PlayGameController {
    private Chapters playerPreference;
    private void initialize() {
        playerPreference = InitialConfiguration.getChapter();
    }
    private void loadFile() {
    }
}
