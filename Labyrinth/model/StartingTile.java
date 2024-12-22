package model;

public class StartingTile extends Corner {
    public StartingTile() {
        super();
    }

    public StartingTile(Direction orientation) {
        super(orientation);
    }

    public StartingTile(Direction orientation, Entity entity) {super(orientation); this._entity = entity;}

    @Override
    public String getType(){
        return "StartingTile";
    }
}
