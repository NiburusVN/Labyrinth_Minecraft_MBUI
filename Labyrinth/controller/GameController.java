package controller;

import model.Game;
import model.GameObserver;

import java.io.IOException;

public class GameController {

    //attribut
    private Game _game;

    public GameController() {this._game = new Game();}

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
    public void validatePuttingExtraTile() throws IOException {
        this._game.moveTilesLine();
    }

    /////////////////////////////
    /// DEPLACEMENT DU JOUEUR ///
    /////////////////////////////
    public void movePlayerUp() throws IOException {
        this._game.movePlayer(new Integer[] {0,-1});
    }
    public void movePlayerDown() throws IOException {
        this._game.movePlayer(new Integer[] {0,1});
    }
    public void movePlayerRight() throws IOException {
        this._game.movePlayer(new Integer[] {1,0});
    }
    public void movePlayerLeft() throws IOException {
        this._game.movePlayer(new Integer[] {-1,0});
    }

    public void rotateExtraTile(Boolean clockwise){
        this._game.rotateExtraTile(clockwise);
    }

    public void moveExtraTile(Boolean clockwise){
        this._game.moveExtraTile(clockwise);
    }

    public void addObserver(GameObserver gameObserver){
        this._game.addObserver(gameObserver);
    }
}
