package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {

    private Integer _playerTurn;
    private ArrayList<Entity> _valableGoals;
    private ArrayList<Player> _players;
    private GameBoard _gameBoard;

    private TileFactory _tileFactory;
    private ArrayList<GameObserver> _observers;

    public Game(){
        this._playerTurn = 0;
        this._valableGoals = new ArrayList<>( Arrays.asList(Entity.values()) );
        this._players = new ArrayList<>(4);
        this._gameBoard = new GameBoard();
        this._observers = new ArrayList<>();
        this._tileFactory = new TileFactory();
    }

    public void startGame(){
        this.initGameBoard();
    }

    public void initGameBoard(){
        this._gameBoard.init_board(this._tileFactory);
    }

    public void distributePlayersGoals(){
        for (Player player : this._players){
            Collections.shuffle(this._valableGoals);
            for(int i = 0; i < 6; i++){
                player.addGoal(this._valableGoals.getFirst());
                this._valableGoals.removeFirst();
            }
        }
    }

    public void nextPlayer(){
        this._playerTurn = (this._playerTurn + 1) % 4;
    }

    //display peut tranmettre un évent au controller avec ce qu'il veut dedans
    public void movePlayer(Integer posX, Integer posY){

        if(this.getCurrentPlayer().getPosX() + posX >= 0 && this.getCurrentPlayer().getPosX() + posX <= 6 && this.getCurrentPlayer().getPosY() + posY >= 0 && this.getCurrentPlayer().getPosY() + posY <= 6) {

            //Si déplacement à gauche|droite|haut|bas de la case du joueur actuel et que l'entrée est accessible:
            if ((posX == -1 && posY == 0 && this._gameBoard.getSpecificTile(this.getCurrentPlayer().getPosX() - 1, this.getCurrentPlayer().getPosY()).getEntries().get(2)) || (posX == 1 && posY == 0 && this._gameBoard.getSpecificTile(this.getCurrentPlayer().getPosX() + 1, this.getCurrentPlayer().getPosY()).getEntries().get(0)) || (posX == 0 && posY == -1 && this._gameBoard.getSpecificTile(this.getCurrentPlayer().getPosX(), this.getCurrentPlayer().getPosY() - 1).getEntries().get(3)) || (posX == 0 && posY == 1 && this._gameBoard.getSpecificTile(this.getCurrentPlayer().getPosX(), this.getCurrentPlayer().getPosY() + 1).getEntries().get(1))) {
                Integer PlayerNextPosX = this.getCurrentPlayer().getPosX() + posX;
                Integer PlayerNextPosY = this.getCurrentPlayer().getPosY() + posY;
                this.getCurrentPlayer().moveTo(PlayerNextPosX, PlayerNextPosY);
            }

            else {
                System.out.println("Tuile non accessible !\n");
            }

        }

        else{
            System.out.println("Vous allez sortir du plateau à force continuer comme ça !\n");
        }

    }

    public void moveTilesLine(Integer posX, Integer posY){
        this._gameBoard.insertExtraTile(posX, posY);

        if(posX == 0){
            for(Player player: this._players){
                if(player.getPosY() == posY && player.getPosY() != 6){
                    player.moveTo(this.getCurrentPlayer().getPosX() + 1, this.getCurrentPlayer().getPosY());
                }

                // Si le joueur est à la dernière case, il retourne à la première case
                else{
                    player.moveTo(0, this.getCurrentPlayer().getPosY());
                }

                this.notifyUpdatePlayerPosition(player.getPosX(), player.getPosY());
            }
        }

        // Sinon si posY de la tuile supplémentaire est à 0
        else{
            for(Player player: this._players){
                if(player.getPosX() == posX && player.getPosY() != 6){
                    player.moveTo(this.getCurrentPlayer().getPosX(), this.getCurrentPlayer().getPosY() + 1);
                }

                // Si le joueur est à la dernière case, il retourne à la première case
                else{
                    player.moveTo(this.getCurrentPlayer().getPosX(), 0);
                }

                this.notifyUpdatePlayerPosition(player.getPosX(), player.getPosY());
            }
        }

    }

    public Player getCurrentPlayer(){
        return this._players.get(this._playerTurn);
    }

    public void addObserver(GameObserver observer){
        this._observers.add(observer);
    }

    public void notifyUpdateInitGameBoard(ArrayList<TileTemplate> gameBoardTiles){
        for(GameObserver observer: this._observers){
            observer.updateInitGameBoard(gameBoardTiles);
        }
    }

    public void notifyUpdateMoveTilesLine(Integer posX, Integer posY){
        for(GameObserver observer: this._observers){
            observer.updateMoveTilesLine(posX, posY);
        }
    }

    public void notifyUpdatePlayerPosition(Integer posX, Integer posY){
        for(GameObserver observer: this._observers){
            observer.updatePlayerPosition(posX, posY);
        }
    }



}
