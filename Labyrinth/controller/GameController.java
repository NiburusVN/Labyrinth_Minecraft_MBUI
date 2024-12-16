package controller;

import model.Game;

public class GameController {
    private Game _game;

    public GameController() {}

    public void movePlayerUp(){
        this._game.movePlayer(0, -1);
    }

    public void movePlayerDown(){
        this._game.movePlayer(0, 1);
    }

    public void movePlayerRight(){
        this._game.movePlayer(1, 0);
    }
    public void movePlayerLeft(){
        this._game.movePlayer(-1, 0);
    }

    public void validatePlayerPosition(){
        this._game.checkWinner();
        this._game.checkGoal();
        this._game.nextPlayer();
    }

    public void validatePuttingExtraTile(Integer posX, Integer posY){
        this._game.moveTilesLine(posX, posY);
    }
}
