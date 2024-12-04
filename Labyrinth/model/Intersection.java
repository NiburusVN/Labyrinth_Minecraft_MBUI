package model;

import java.util.Arrays;
import java.util.List;

public class Intersection extends TileTemplate {
    public Intersection() {
        super();
    }

    public Intersection(Direction orientation) {
        super(orientation);
    }

    @Override
    public void setOrientation(Direction orientation) {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        this._orientation = orientation;

        switch (this._orientation) {
            case Direction.North:
                this.setEntries(Arrays.asList(false, true, true, true));
                break;

            case Direction.South:
                this.setEntries(Arrays.asList(true, true, false, true));
                break;

            case Direction.East:
                this.setEntries(Arrays.asList(true, false, true, true));
                break;

            case Direction.West:
                this.setEntries(Arrays.asList(true, true, true, false));
                break;
        }
    }
}
