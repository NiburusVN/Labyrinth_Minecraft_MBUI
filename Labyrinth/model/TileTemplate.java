package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TileTemplate {

    //Attributs
    private static final Random rand = new Random();
    protected Direction _orientation;
    protected List<Boolean> _entries;
    protected Entity _entity = null; //default

    /// Constructeurs ///
    public TileTemplate() {
        int r = rand.nextInt(4); //utilisation d'aléatoire
        switch (r) {
            case 0 -> this.setOrientation(Direction.North);
            case 1 -> this.setOrientation(Direction.East);
            case 2 -> this.setOrientation(Direction.South);
            case 3 -> this.setOrientation(Direction.West);
        }
    }

    public TileTemplate(Direction orientation) {
        this.setOrientation(orientation);
    }

    /// GETs ///
    public abstract List<Boolean> getEntries();

    public Entity getEntity(){
        return this._entity;
    }

    public Direction getOrientation(){
        return this._orientation;
    }

    /// SETs ///
    public void setEntity(Entity entity){
        this._entity = entity;
    }

    public void setOrientation(Direction orientation){
        this._orientation = orientation;
    }

    /// REMOVE ///
    //c'est pour une optique d'optimisation
    //lorsqu'il y a un objectif sur une case, on fait une vérification -> on en fait plus
    public void removeEntity(Entity entity){
        this._entity = null;
    }
}
