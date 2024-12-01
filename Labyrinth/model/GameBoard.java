package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<List<TileTemplate>> _boardTiles;
    private TileTemplate _extraTile;

    public GameBoard() {
        this._boardTiles = new ArrayList<>();
        for(int i = 0; i<49; i++){
            this._boardTiles.add(new ArrayList<>());
        }

        this._extraTile = null;
    }

    public void init_board(TileFactory tileFactory){}

    public void insertExtraTile(Integer posX, Integer posY){}

    public TileTemplate getExtraTile(){
        return this._extraTile;
    }

}
