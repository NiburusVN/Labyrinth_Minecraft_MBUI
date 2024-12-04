package model;

import java.util.Arrays;
import java.util.List;

public class Hallway extends TileTemplate {

    public Hallway() {
        super();
    }

    public Hallway(Direction orientation) {
        super(orientation);
    }

    @Override
    public void setOrientation(Direction orientation) {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        this._orientation = orientation;

        switch (this._orientation) {
            case Direction.North:
            case Direction.South:
                this.setEntries(Arrays.asList(false, true, false, true));
                break;

            case Direction.East:
            case Direction.West:
                this.setEntries(Arrays.asList(false, true, false, true));
                break;
        }
    }
}
