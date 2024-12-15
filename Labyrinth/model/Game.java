package model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Entity> _valableGoals;
    private ArrayList<Player> _players;
    private GameBoard _gameBoard;

    private ArrayList<GameObserver> _observers;

    public Game(){
        this._valableGoals = new ArrayList<>(24);
        this._players = new ArrayList<>(4);
        this._gameBoard = new GameBoard();
        this._observers = new ArrayList<>();
    }




}
