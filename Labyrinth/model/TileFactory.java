package model;

public class TileFactory {

    public TileFactory() {}

    public TileTemplate createCorner(){return new Corner();}
    public TileTemplate createStartingTile(Direction east){return new StartingTile();}
    public TileTemplate createIntersection(){return new Intersection();}
    public TileTemplate createHallway(){return new Hallway();}


}
