package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Corner extends TileTemplate {

    public Corner() {
        super();
    }

    //surcharge du constructeur (niveau 1 des tuiles: on choisi l'orientation)
    public Corner(Direction orientation) {
        super(orientation);
    }

    @Override
    //Fonction pour vérifier la validité des déplacements
    public ArrayList<Boolean> getEntries() {
        // 1er indice : Ouest, 2ème indice : Nord, 3ème indice: Est, 4ème indice: Sud
        ArrayList<Boolean> entries = new ArrayList<Boolean>();

        switch (this._orientation) {
            case Direction.NORTH:
                entries.addAll(Arrays.asList(false, true, true, false));
                break;

            case Direction.SOUTH:
                entries.addAll(Arrays.asList(true, false, false, true));
                break;

            case Direction.EAST:
                entries.addAll(Arrays.asList(false, false, true, true));
                break;

            case Direction.WEST:
                entries.addAll(Arrays.asList(true, true, false, false));
                break;

        }
        return entries;
    }

    @Override
    public String getType() {
        return "Corner";
    }

}
