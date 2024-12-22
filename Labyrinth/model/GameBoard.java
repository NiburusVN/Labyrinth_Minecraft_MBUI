package model;

import java.util.*;


public class GameBoard {

    //attributs
    private List<List<TileTemplate>> _boardTiles;
    private TileTemplate _extraTile; //la 50e tuile
    private Integer[] _extraTilePosition = new Integer[] {0, 2};

    public GameBoard() {
        this._boardTiles = new ArrayList<>(9);
        for(int i = 0; i<9; i++){
            this._boardTiles.add(Arrays.asList(null, null, null, null, null, null, null, null, null));
        }

        this._extraTile = null;
    }

    /////////////////////////////////
    /// INITIALISATION DU TERRAIN ///
    /////////////////////////////////
    //Fonction pour Générer les tuiles et remplir le terrain de façon aléatoire
    public void initBoard(TileFactory tileFactory, List<Entity> entities){

        //Création d'une Liste pour stocker toutes les tuiles pour ensuite les redistribuer
        List<TileTemplate> tiles = new ArrayList<>(34);

        for(int i = 0; i<16; i++)
            tiles.add(tileFactory.createCorner());
        for (int i = 0; i < 12; i++)
            tiles.add(tileFactory.createHallway());
        for (int i = 0; i < 6; i++)
            tiles.add(tileFactory.createIntersection());

        Collections.shuffle(tiles);

        //initialisation des coins
        this.initCorners(tileFactory, entities);

        for (int y = 1; y < 8; y++) { //chaque ligne sauf 0 et 8.
            for (int x = 1; x < 8; x++) { //7 colonnes d'une ligne
                if (!(y == 1 && x == 1) &&
                        !(y == 1 && x == 7) &&
                        !(y == 7 && x == 1) &&
                        !(y == 7 && x == 7)) { // si différent des coins

                    if (y % 2 == 1 && x % 2 == 1) { //si paire-paire → intersection
                        if(y == 1) //si sur le bord gauche
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.NORTH));

                        else if(y == 7) //si sur le bord droit
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.SOUTH));

                        else if(x == 1) //si sur le bord haut
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.EAST));

                        else if(x == 7) //si sur le bord bas
                            this._boardTiles.get(y).set(x, tileFactory.createIntersection(Direction.WEST));

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
        this._extraTile = tiles.getFirst();
        this._boardTiles.get(0).set(2, this._extraTile);
    }

    public void initCorners(TileFactory tileFactory, List<Entity> entities){
        this._boardTiles.get(1).set(1, tileFactory.createStartingTile(Direction.EAST, entities.getFirst()));// Coin fixe 1
        entities.removeFirst();
        this._boardTiles.get(1).set(7, tileFactory.createStartingTile(Direction.SOUTH, entities.getFirst())); // Coin fixe 2
        entities.removeFirst();
        this._boardTiles.get(7).set(1, tileFactory.createStartingTile(Direction.NORTH, entities.getFirst())); // Coin fixe 3
        entities.removeFirst();
        this._boardTiles.get(7).set(7, tileFactory.createStartingTile(Direction.WEST, entities.getFirst())); // Coin fixe 4
        entities.removeFirst();
    }

    public void goalsDistribution(List<Entity> entities){
        Collections.shuffle(entities);//on mélange les objectifs
        Random rand = new Random();
        int valX, valY;
        while(!entities.isEmpty()){
            valX = rand.nextInt(7)+1; valY = rand.nextInt(7)+1;
            if(!(valX == 1 && valY == 1) &&
                    !(valX == 7 && valY == 1) &&
                    !(valX == 1 && valY == 7) &&
                    !(valX == 7 && valY == 7)){ //si ce ne sont pas les coins
                if(this._boardTiles.get(valY).get(valX).getEntity() == null){ //y = val/7 ; x = val%7
                    this._boardTiles.get(valY).get(valX).setEntity(entities.getFirst());
                    entities.removeFirst();
                }
            }
        }
    }

    //////////////////////////////
    /// ACTIONS SUR LE TERRAIN ///
    //////////////////////////////

    /// InsertExtraTile est le décalage du terrain lors du placement de la 50e tuile
    /// On peut savoir dans quel axe va bouger le terrain via la valeur de X ou Y étant une extrémité.
    public Integer[] insertExtraTile() {
        Integer[] position = this._extraTilePosition;

        if (position[0] == 0) { //du haut vers le bas
            for(int i = 0; i<8; i++){
                this._boardTiles.get(8-i).set(position[1],this._boardTiles.get(7-i).get(position[1]));
            }
            this._extraTile = this._boardTiles.get(8).get(position[1]);
            this._extraTilePosition = new Integer[] {8, position[1]};
        }

        else if(position[0] == 8){ //du bas vers le haut
            for(int i = 0; i<8; i++){
                this._boardTiles.get(i).set(position[1],this._boardTiles.get(i+1).get(position[1]));
            }
            this._extraTile = this._boardTiles.get(0).get(position[1]);
            this._extraTilePosition = new Integer[] {0, position[1]};
        }

        else if(position[1] == 0){ //de la gauche vers la droite
            for(int i = 0; i<8; i++){
                this._boardTiles.get(position[0]).set(8-i, this._boardTiles.get(position[0]).get(7-i));
            }
            this._extraTile = this._boardTiles.get(position[0]).get(8);
            this._extraTilePosition = new Integer[] {position[0], 8};
        }

        else if(position[1] == 8){ //de la gauche vers la droite
            for(int i = 0; i<8; i++){
                this._boardTiles.get(position[0]).set(i, this._boardTiles.get(position[0]).get(i+1));
            }
            this._extraTile = this._boardTiles.get(position[0]).get(0);
            this._extraTilePosition = new Integer[] {position[0], 0};
        }

        else{
            System.out.println("Erreur dans insertExtraTile ; les positions données sont invalides\n" +
                    "X = "+ position[0] + " et Y = " + position[1] + "\n");
            //notifyMessage
        }
        return this._extraTilePosition;
    }

    //déplace la 50e pièce ; true → dans le sens des aiguilles d'une montre, sinon l'autre sens
    public void moveExtraTile(Boolean clockwise){
        if(clockwise){ // Sens des aiguilles d'une montre.
            //Il faut savoir sur quel bord est la 50e pièce
            if(this._extraTilePosition[0] == 0) { //sur le bord en haut
                if(this._extraTilePosition[1]== 6){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {2,8};
                }
                else{ //reste sur le même bord
                    this._extraTilePosition[1] += 2;
                }
            }
            else if(this._extraTilePosition[0] == 8) { //sur le bord en bas
                if(this._extraTilePosition[1]== 2){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {6,0};
                }
                else{ //reste sur le même bord
                    this._extraTilePosition[1] -= 2;
                }
            }
            else if(this._extraTilePosition[1] == 0){ //sur le bord à gauche
                if(this._extraTilePosition[0] == 2){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {0,2};
                }
                else { //reste sur le même bord
                    this._extraTilePosition[0] -= 2;
                }
            }
            else if(this._extraTilePosition[1] == 8){ //sur le bord à gauche
                if(this._extraTilePosition[0] == 6){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {8,6};
                }
                else { //reste sur le même bord
                    this._extraTilePosition[0] += 2;
                }
            }
        }
        else{
            //Il faut savoir sur quel bord est la 50e pièce
            if(this._extraTilePosition[0] == 0) { //sur le bord en haut
                if(this._extraTilePosition[1]== 2){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {2,0};
                }
                else{ //reste sur le même bord
                    this._extraTilePosition[1] -= 2;
                }
            }
            else if(this._extraTilePosition[0] == 8) { //sur le bord en bas
                if(this._extraTilePosition[1]== 6){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {6,8};
                }
                else{ //reste sur le même bord
                    this._extraTilePosition[1] += 2;
                }
            }
            else if(this._extraTilePosition[1] == 0){ //sur le bord à gauche
                if(this._extraTilePosition[0] == 6){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {8,2};
                }
                else { //reste sur le même bord
                    this._extraTilePosition[0] += 2;
                }
            }
            else if(this._extraTilePosition[1] == 8){ //sur le bord à gauche
                if(this._extraTilePosition[0] == 2){ //au bout → change de bord
                    this._extraTilePosition = new Integer[] {0,6};
                }
                else { //reste sur le même bord
                    this._extraTilePosition[0] -= 2;
                }
            }
        }
    }

    //tourne la 50e pièce ; true → dans le sens des aiguilles d'une montre, sinon l'autre sens.
    public void rotateExtraTile(Boolean clockwise){
        Direction newOrientation = switch (this._extraTile.getOrientation()) {
            case NORTH -> clockwise ? Direction.EAST : Direction.WEST;
            case SOUTH -> clockwise ? Direction.WEST : Direction.EAST;
            case EAST -> clockwise ? Direction.SOUTH : Direction.NORTH;
            case WEST -> clockwise ? Direction.NORTH : Direction.SOUTH;
        };
        this._extraTile.setOrientation(newOrientation);
    }

    /// GETs ///
    public TileTemplate getExtraTile(){return this._extraTile;}

    public Integer[] getCoordinates(){
        return this._extraTilePosition;
    }

    public TileTemplate getSpecificTile(Integer[] pos){
        return this._boardTiles.get(pos[0]).get(pos[1]);
    }

    public List<List<TileTemplate>> getGameBoardTiles(){
        return this._boardTiles;
    }
}
