package model;

public class TileFactory {
    public TileFactory() {}

    public BoardTile createCorner(){return new Corner();}
    public BoardTile createStartingTile(){return new StartingTile();}
    public BoardTile createIntersection(){return new Intersection();}
    public BoardTile createHallway(){return new Hallway();}


}
