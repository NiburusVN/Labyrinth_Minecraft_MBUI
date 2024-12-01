package model;

import java.util.Arrays;
import java.util.List;

public class Corner extends TileTemplate {
    public Corner() {
        super();
    }

    @Override
    public void setOrientation(Direction orientation) {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        this._orientation = orientation;

        switch (this._orientation) {
            case Direction.North:
                this.setEntries(Arrays.asList(false, true, true, false));
                break;

            case Direction.South:
                this.setEntries(Arrays.asList(true, false, false, true));
                break;

            case Direction.East:
                this.setEntries(Arrays.asList(false, false, true, true));
                break;

            case Direction.West:
                this.setEntries(Arrays.asList(true, true, false, false));
                break;
        }
    }

}
