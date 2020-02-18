package sample.Model;

public enum Level {
    EASY("Easy"),
    MED("Medium"),
    HARD("Hard"); //Level.values()

    private String level;

    Level(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override public String toString() {
        return level;
    }
}