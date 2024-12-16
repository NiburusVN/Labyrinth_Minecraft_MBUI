package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private List<List<TileTemplate>> _boardTiles;
    private TileTemplate _extraTile;
    private ArrayList<GameObserver> _observers;

    public GameBoard() {
        this._boardTiles = new ArrayList<>(7);
        for(int i = 0; i<7; i++){
            this._boardTiles.add(new ArrayList<>(7));
        }

        this._extraTile = null;

        this._observers = new ArrayList<GameObserver>();
    }

    public void init_board(TileFactory tileFactory){
        Random random = new Random();

        int movableCorners = 16;
        int movableHallways = 12;
        int movableIntersections = 6;

        this._boardTiles.get(0).set(0, tileFactory.createStartingTile(Direction.East)); // Coin fixe 1
        this._boardTiles.get(0).set(6, tileFactory.createStartingTile(Direction.South)); // Coin fixe 2
        this._boardTiles.get(6).set(0, tileFactory.createStartingTile(Direction.North)); // Coin fixe 3
        this._boardTiles.get(6).set(6, tileFactory.createStartingTile(Direction.West)); // Coin fixe 4

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (!(y == 0 && x == 0) && !(y == 0 && x == 6) && !(y == 6 && x == 0) && !(y == 6 && x == 6)) {

                    if ((y % 2 != 0 && x % 2 == 0) || (y % 2 != 0 && x % 2 != 0)) {
                        this._boardTiles.get(y).set(x, tileFactory.createIntersection());

                    } else {
                        int tileType = random.nextInt(3); // nombre random entre 0 et 2
                        if (tileType == 0 && movableCorners > 0) {
                            this._boardTiles.get(y).set(x, tileFactory.createCorner());
                            movableCorners--;
                        }

                        else if (tileType == 1 && movableHallways > 0) {
                            this._boardTiles.get(y).set(x, tileFactory.createHallway());
                            movableHallways--;
                        }

                        else if (tileType == 2 && movableIntersections > 0) {
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection());
                            movableIntersections--;
                        }

                        else {
                        // Si jamais on peut plus faire en mode random, car il n'y en a aucune de disponible
                            if (movableCorners > 0) {
                                this._boardTiles.get(y).set(x, tileFactory.createCorner());
                                movableCorners--;
                            } else if (movableHallways > 0) {
                                this._boardTiles.get(y).set(x, tileFactory.createHallway());
                                movableHallways--;
                            } else if (movableIntersections > 0) {
                                this._boardTiles.get(y).set(x, tileFactory.createIntersection());
                                movableIntersections--;
                            }
                        }
                    }
                }
            }
        }
    }

    public void insertExtraTile(Integer posX, Integer posY){

    }

    public TileTemplate getExtraTile(){
        return this._extraTile;
    }

    public TileTemplate getSpecificTile(Integer posX, Integer posY){
        return this._boardTiles.get(posY).get(posX);
    }

}
