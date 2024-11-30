package model;

import java.util.Random;

public abstract class BoardTile {
    private static Random rand = new Random();
    Direction _orientation;
    Entity _entity = null; //default
    public BoardTile() {
        Integer r = rand.nextInt(4);
        switch (r) {
            case 0 -> _orientation = Direction.North;
            case 1 -> _orientation = Direction.East;
            case 2 -> _orientation = Direction.South;
            case 3 -> _orientation = Direction.West;
        }
    }
}
