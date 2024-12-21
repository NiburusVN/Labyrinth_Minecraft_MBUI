package controller;

import model.Game;

public class GameController {

    //attribut
    private Game _game;

    public GameController(Game game) {this._game = game;}

    /////////////////////////
    /// ACTIONS DU JOUEUR ///
    /////////////////////////
    //valider la position du joueurs pour passer à la suite
    public void validatePlayerPosition(){
        this._game.nextPlayer();
    }

    //valider le sens de déplacement du labyrinth
    public void validatePuttingExtraTile(Integer posX, Integer posY){
        this._game.moveTilesLine(posX, posY);
    }

    /////////////////////////////
    /// DEPLACEMENT DU JOUEUR ///
    /////////////////////////////
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

    ///////////////////////////////////////
    /// VERIFICATION D'OBJECTIF ATTEINT ///
    ///////////////////////////////////////
    public void validatePlayerPosition(){
        this._game.checkWinner();
        this._game.checkGoal();
        this._game.nextPlayer();
    }

    public void validatePuttingExtraTile(Integer posX, Integer posY){
        this._game.moveTilesLine(posX, posY);
    }
}
