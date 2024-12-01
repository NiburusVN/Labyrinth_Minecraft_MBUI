package model;

import java.util.List;
import java.util.Random;

public abstract class TileTemplate {
    private static Random rand = new Random();
    protected Direction _orientation;
    protected List<Boolean> _entries;
    protected Entity _entity = null; //default


    public TileTemplate() {
        Integer r = rand.nextInt(4);
        switch (r) {
            case 0 -> this.setOrientation(Direction.North);
            case 1 -> this.setOrientation(Direction.East);
            case 2 -> this.setOrientation(Direction.South);
            case 3 -> this.setOrientation(Direction.West);
        }
    }

    public abstract void setOrientation(Direction orientation);

    public Direction getOrientation(){
        return this._orientation;
    }

    public void setEntity(Entity entity){
        this._entity = entity;
    }

    public Entity getEntity(){
        return this._entity;
    }

    public void setEntries(List<Boolean> entries){
        this._entries.clear();
        this._entries.addAll(entries);
    }

}
