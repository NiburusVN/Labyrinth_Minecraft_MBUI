package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.random.*;


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

        List<TileTemplate> tiles = new ArrayList<>(34);

        for(int i = 0; i<16; i++)
            tiles.add(tileFactory.createCorner());
        for (int i = 0; i < 12; i++)
            tiles.add(tileFactory.createHallway());
        for (int i = 0; i < 6; i++)
            tiles.add(tileFactory.createIntersection());

        Collections.shuffle(tiles);

        this._boardTiles.get(0).set(0, tileFactory.createStartingTile(Direction.East)); // Coin fixe 1
        this._boardTiles.get(0).set(6, tileFactory.createStartingTile(Direction.South)); // Coin fixe 2
        this._boardTiles.get(6).set(0, tileFactory.createStartingTile(Direction.North)); // Coin fixe 3
        this._boardTiles.get(6).set(6, tileFactory.createStartingTile(Direction.West)); // Coin fixe 4

        for (int y = 0; y < 7; y++) { //chaque ligne
            for (int x = 0; x < 7; x++) { //7 colonnes d'une ligne
                if (!(y == 0 && x == 0) && !(y == 0 && x == 6) && !(y == 6 && x == 0) && !(y == 6 && x == 6)) { // si différent des coins

                    if (y % 2 == 0 && x % 2 == 0) { //si paire-paire → intersection
                        if(y == 0) //si sur le bord gauche
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.North));

                        else if(y == 6) //si sur le bord droit
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.South));

                        else if(x == 0) //si sur le bord haut
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.East));

                        else if(x == 6) //si sur le bord bas
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.West));

                        else //si au milieu (direction aléatoire)
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection());
                    }
                    else { // sinon autre
                        this._boardTiles.get(y).set(x, tiles.getFirst());
                        tiles.removeFirst();
                    }
                }
            }
        }
    }

    public void goals_distribution(List<Entity> entities){
        Collections.shuffle(entities);
        Random rand = new Random();
        int val;
        while(!entities.isEmpty()){
            val = rand.nextInt(50);
            if(val == 0 || val == 6 || val == 42 || val == 48){ //si c'est les coins
                continue;
            }
            else if(val == 49){
                if(this._extraTile.getEntity() == null){
                    this._extraTile.setEntity(entities.getFirst());
                    entities.removeFirst();
                }
            }
            else{
                if(this._boardTiles.get(val/7).get(val%7).getEntity() == null){ //y = val/7 ; x = val%7
                    this._boardTiles.get(val/7).get(val%7).setEntity(entities.getFirst());
                    entities.removeFirst();
                }
            }
        }
    }

    /// InsertExtraTile est le décalage du terrain lors du placement de la 50e tuile
    /// posX et posY correspondent à l'entrée de la 50e tuile
    public void insertExtraTile(Integer posX, Integer posY){
        TileTemplate oldTile;
        if(posY == 0){ //du haut vers le bas
            oldTile = this._boardTiles.get(6).get(posX); // récupération de la tuile de sortie

            for(int i = 1; i <= 6 ; i++)
                this._boardTiles.get(7-i).set(posX, this._boardTiles.get(6-i).get(posX)); //décalage des tuiles

            this._boardTiles.get(0).set(posX, _extraTile); //ajout de la tuile
            this._extraTile = oldTile; //nouvelle tuile à placer
        }
        else if(posY == 6){ //du bas vers le haut
            oldTile = this._boardTiles.get(0).get(posX);

            for(int i = 0; i < 6 ; i++)
                this._boardTiles.get(i).set(posX, this._boardTiles.get(i+1).get(posX));

            this._boardTiles.get(6).set(posX, _extraTile);
            this._extraTile = oldTile;
        }
        else if(posX == 0){ //de la gauche vers la droite
            oldTile = this._boardTiles.get(posY).get(6);

            for(int i = 1; i <= 6 ; i++)
                this._boardTiles.get(posY).set(7-i, this._boardTiles.get(posY).get(6-i));

            this._boardTiles.get(posY).set(0, this._extraTile);
            this._extraTile = oldTile;
        }
        else if(posX == 6){ //de la droite vers la gauche
            oldTile = this._boardTiles.get(posY).get(0);

            for(int i = 0; i < 6 ; i++)
                this._boardTiles.get(posY).set(i, this._boardTiles.get(posY).get(i+1));

            this._boardTiles.get(posY).set(6, _extraTile);
            this._extraTile = oldTile;
        }
        else{
            System.out.println("Erreur dans insertExtraTile ; les positions données sont invalides\n" +
                    "X = "+ posX + " et Y = " + posY + "\n");
        }
    }

    public TileTemplate getExtraTile(){
        return this._extraTile;
    }

    public TileTemplate getSpecificTile(Integer posX, Integer posY){
        return this._boardTiles.get(posY).get(posX);
    }

}
