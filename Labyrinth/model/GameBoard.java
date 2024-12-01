package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<List<TileTemplate>> _boardTiles;
    private TileTemplate _extraTile;

    public GameBoard() {
        this._boardTiles = new ArrayList<>(7);
        for(int i = 0; i<7; i++){
            this._boardTiles.add(new ArrayList<>(7));
        }

        this._extraTile = null;
    }

    public void init_board(TileFactory tileFactory){
        this._boardTiles.get(0).set(0, tileFactory.createStartingTile(Direction.East)); // Coin fixe 1
        this._boardTiles.get(0).set(6, tileFactory.createStartingTile(Direction.South)); // Coin fixe 2
        this._boardTiles.get(6).set(0, tileFactory.createStartingTile(Direction.North)); // Coin fixe 3
        this._boardTiles.get(6).set(6, tileFactory.createStartingTile(Direction.West)); // Coin fixe 4
        
    }

    public void insertExtraTile(Integer posX, Integer posY){}

    public TileTemplate getExtraTile(){
        return this._extraTile;
    }

}
