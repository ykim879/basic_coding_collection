//package application;

public class Move {
    private String name;
    private int power;
    private String type;

    public Move(String n, int p, String t) {
        name = n;
        power = p;
        type = t;

    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }
}
