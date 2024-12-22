package model;

public class TileFactory {

    public TileFactory() {}

    //Création de Tuile avec une orientation aléatoire
    public TileTemplate createCorner(){return new Corner();}
    public TileTemplate createStartingTile(){return new StartingTile();}
    public TileTemplate createIntersection(){return new Intersection();}
    public TileTemplate createHallway(){return new Hallway();}

    //Création avec une orientation choisie.
    public TileTemplate createCorner(Direction orientation){return new Corner(orientation);}
    public TileTemplate createStartingTile(Direction orientation){return new StartingTile(orientation);}
    public TileTemplate createIntersection(Direction orientation){return new Intersection(orientation);}
    public TileTemplate createHallway(Direction orientation){return new Hallway(orientation);}

    //Création de Startingtile avec Orientation et Entity défini
    public TileTemplate createStartingTile(Direction orientation, Entity entity){return new StartingTile(orientation, entity);}
}
