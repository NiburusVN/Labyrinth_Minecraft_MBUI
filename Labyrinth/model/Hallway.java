package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public ArrayList<Boolean> getEntries() {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        ArrayList<Boolean> entries = new ArrayList<Boolean>();

        switch (this._orientation) {

            case Direction.North:
            case Direction.South:
                entries.addAll(Arrays.asList(false, true, false, true));

            case Direction.East:
            case Direction.West:
                entries.addAll(Arrays.asList(true, false, true, false));

        }
        return entries;
    }
}
