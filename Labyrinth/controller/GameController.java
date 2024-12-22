package controller;

import model.Game;

import java.io.IOException;

public class GameController {

    //attribut
    private Game _game;

    public GameController(Game game) {this._game = game;}

    //initialisation d'un partie de Labyrinth
    public void play() throws IOException {_game.startGame();}

    /////////////////////////
    /// ACTIONS DU JOUEUR ///
    /////////////////////////
    //valider la position du joueurs pour passer à la suite
    public void validatePlayerPosition(){
        this._game.nextPlayer();
    }

    //valider le sens de déplacement du labyrinth
    public void validatePuttingExtraTile(Integer[] insertPos){
        this._game.moveTilesLine(insertPos);
    }

    /////////////////////////////
    /// DEPLACEMENT DU JOUEUR ///
    /////////////////////////////
    public void movePlayerUp(){
        this._game.movePlayer(new Integer[] {0,-1});
    }
    public void movePlayerDown(){
        this._game.movePlayer(new Integer[] {0,1});
    }
    public void movePlayerRight(){
        this._game.movePlayer(new Integer[] {1,0});
    }
    public void movePlayerLeft(){
        this._game.movePlayer(new Integer[] {-1,0});
    }
}
