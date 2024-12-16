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
        if(this.getCurrentPlayer().getPosX()    this._gameBoard.getSpecificTile(posX, posY))
    }

    public Player getCurrentPlayer(){
        return this._players.get(this._playerTurn);
    }



}
