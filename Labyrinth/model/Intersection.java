package model;

import java.util.ArrayList;
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
    public ArrayList<Boolean> getEntries() {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        ArrayList<Boolean> entries = new ArrayList<Boolean>();

        switch (this._orientation) {
            case Direction.North:
                entries.addAll(Arrays.asList(false, true, true, true));
                break;

            case Direction.South:
                entries.addAll(Arrays.asList(true, true, false, true));
                break;

            case Direction.East:
                entries.addAll(Arrays.asList(true, false, true, true));
                break;

            case Direction.West:
                entries.addAll(Arrays.asList(true, true, true, false));
                break;

        }
        return entries;
    }
}
