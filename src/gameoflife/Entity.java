package gameoflife;

public class Entity {

    private boolean[][] field;
    private String name;

    public Entity(boolean[][] field, String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return name;

    }

}