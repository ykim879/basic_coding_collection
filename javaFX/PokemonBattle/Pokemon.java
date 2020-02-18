//package application;

public class Pokemon {
    private String name;
    private int level;
    private double maxHP;
    private double currentHP;
    private int atk;
    private Move[] moves;
    private String type;
    private boolean fainted;

    public Pokemon(String n, int l, double h, int a, String t, Move... move) {
        name = n;
        level = l;
        maxHP = h;
        atk = a;
        type = t;
        currentHP = maxHP;
        moves = move;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getAtk() {
        return atk;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public double getCurrentHP() {
        return currentHP;
    }

    public Move[] getMoves() {
        return moves;
    }

    public void setCurrentHP(double currentHP) {
        this.currentHP = currentHP;
        if (this.currentHP <= 0) {
            fainted = true;
        }
    }

    public double compareType(Move move) {
        if (move.getType().equals(type)) {
            return 0.5;
        } else if (move.getType().equals("WATER")) {
            if (type.equals("FIRE")) {
                return 2.0;
            } else if (type.equals("GRASS")) {
                return 0.5;
            }
        } else if (move.getType().equals("GRASS")) {
            if (type.equals("WATER")) {
                return 2.0;
            } else if (type.equals("FIRE")) {
                return 0.5;
            } else if (type.equals("FLYING")) {
                return 0.5;
            }
        } else if (move.getType().equals("FIRE")) {
            if (type.equals("GRASS")) {
                return 2.0;
            } else if (type.equals("WATER")) {
                return 0.5;
            }
        } else if (move.getType().equals("FLYING")) {
            if (type.equals("GRASS")) {
                return 2.0;
            }
        }
        return 1;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }
}
