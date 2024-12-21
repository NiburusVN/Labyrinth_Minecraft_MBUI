package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game{
//    public class Game {

    //Attributs
    private Integer _playerTurn;
    private ArrayList<Entity> _valableGoals;
    private ArrayList<Player> _players;
    private GameBoard _gameBoard;

    private TileFactory _tileFactory;
    private ArrayList<GameObserver> _observers;

    //constructeur
    public Game(){
        this._playerTurn = 0;
        this._valableGoals = new ArrayList<>( Arrays.asList(Entity.values()) );
        this._players = new ArrayList<>(List.of(new Player(), new Player(), new Player(), new Player()));
        this._gameBoard = new GameBoard();
        this._observers = new ArrayList<>();
        this._tileFactory = new TileFactory();
    }

    //////////////////////////////////
    /// FONCTIONS D'INITIALISATION ///
    //////////////////////////////////
    //fonction init pour commencer un jeu
    public void startGame(){
        this.initGameBoard();//terrain
        this.initGoals();//objectifs sur terrain
        this.distributePlayersGoals();//objectifs des joueurs
        this.notifyAll();
    }

    //fonction qui crée les tuiles pour le terrain
    public void initGameBoard(){
        this._gameBoard.init_board(this._tileFactory);
    }

    public void initGoals(){this._gameBoard.goals_distribution(this._valableGoals);}

    //fonction pour assigner les objectifs aux joueurs
    public void distributePlayersGoals(){
        for (Player player : this._players){
            Collections.shuffle(this._valableGoals);
            for(int i = 0; i < 6; i++){
                player.addGoal(this._valableGoals.getFirst());
                this._valableGoals.removeFirst();
            }
        }
    }

    //ajout d'un oberver
    public void addObserver(GameObserver observer){
        this._observers.add(observer);
    }

    /////////////////////////////////
    /// FONCTIONS SUR LES JOUEURS ///
    /////////////////////////////////
    //fonction pour passer au tour du joueur suivant
    public void nextPlayer(){
        this.checkWinner();
        this.checkGoal();
        this._playerTurn = (this._playerTurn + 1) % 4;
        //notify?
    }

    //fonction qui return le joueur qui fait son tour
    public Player getCurrentPlayer(){
        return this._players.get(this._playerTurn);
    }

    //fonction sur le déplacement du joueur
    //display peut tranmettre un évent au controller avec ce qu'il veut dedans
    public void movePlayer(Integer posX, Integer posY){
        if(this.getCurrentPlayer().getPosX() + posX >= 0
                && this.getCurrentPlayer().getPosX() + posX <= 6
                && this.getCurrentPlayer().getPosY() + posY >= 0
                && this.getCurrentPlayer().getPosY() + posY <= 6) {//vérification d'un déplacement légal

            Integer X = this.getCurrentPlayer().getPosX();
            Integer Y = this.getCurrentPlayer().getPosY();

            //0 ouest ; 1 nord ; 2 est ; 3 sud
            if (posX == -1) { // déplacemenet vers Ouest
                if (this._gameBoard.getSpecificTile(X, Y).getEntries().get(0) //si notre case permet le déplacement
                        && this._gameBoard.getSpecificTile(X - 1, Y).getEntries().get(2)) { // si la case cible permet le déplacement
                    this.getCurrentPlayer().moveTo(X - 1, Y);
                }
            }
            if (posX == 1) { // déplacement vers Est
                if (this._gameBoard.getSpecificTile(X, Y).getEntries().get(2) //si notre case permet le déplacement
                        && this._gameBoard.getSpecificTile(X + 1, Y).getEntries().get(0)) { // si la case cible permet le déplacement
                    this.getCurrentPlayer().moveTo(X + 1, Y);
                }
            }
            if (posX == -1) { // déplacement vers Nord
                if (this._gameBoard.getSpecificTile(X, Y).getEntries().get(1) //si notre case permet le déplacement
                        && this._gameBoard.getSpecificTile(X, Y - 1).getEntries().get(3)) { // si la case cible permet le déplacement
                    this.getCurrentPlayer().moveTo(X, Y - 1);
                }
            }
            if (posX == -1) { // déplacement vers le Sud
                if (this._gameBoard.getSpecificTile(X, Y).getEntries().get(3) //si notre case permet le déplacement
                        && this._gameBoard.getSpecificTile(X, Y + 1).getEntries().get(1)) { // si la case cible permet le déplacement
                    this.getCurrentPlayer().moveTo(X, Y + 1);
                }
            }
            else {
                System.out.println("Tuile non accessible !\n");
            }
        }

        else{
            System.out.println("Vous allez sortir du plateau à force continuer comme ça !\n");
        }
    }

    ////////////////////////////////
    /// FONCTIONS SUR LE TERRAIN ///
    ////////////////////////////////
    public void moveTilesLine(Integer posX, Integer posY) {
        //déplacement des tuiles du labyrinth
        this._gameBoard.insertExtraTile(posX, posY);

        //Déplacement des joueurs sur la ligne décalé + vérification d'éjection en dehors du terrain
        if (posX == 0) { //si déplacement de gauche vers droite
            for (Player player : this._players) {//pour tous les joueurs
                if (player.getPosY() == posY && player.getPosX() != 6) { //si le joueur est déplacé et qu'il N'EST PAS au bout à droite
                    player.moveTo(this.getCurrentPlayer().getPosX() + 1, this.getCurrentPlayer().getPosY());
                } else {//le joueur est à la dernière case, il retourne à la première case
                    player.moveTo(0, this.getCurrentPlayer().getPosY());
                }
            }
        } else if (posX == 6) { //si le déplacement est de droite vers la gauche
            for (Player player : this._players) {//pour tous les joueurs
                if (player.getPosY() == posY && player.getPosX() != 0) { //si le joueur est déplacé et qu'il N'EST PAS au bout à gauche
                    player.moveTo(this.getCurrentPlayer().getPosX() - 1, this.getCurrentPlayer().getPosY());
                } else {//le joueur est à la première case, il retourne à la dernière case
                    player.moveTo(6, this.getCurrentPlayer().getPosY());
                }
            }
        } else if (posY == 0) {//si le déplacement de haut vers le bas
            for (Player player : this._players) {//pour tous les joueurs
                if (player.getPosX() == posX && player.getPosX() != 6) { //si le joueur est déplacé et qu'il N'EST PAS tout en bas
                    player.moveTo(this.getCurrentPlayer().getPosX(), this.getCurrentPlayer().getPosY() + 1);
                } else {//le joueur est tout en bas, il retourne tout en haut
                    player.moveTo(this.getCurrentPlayer().getPosX(), 0);
                }
            }
        } else if (posY == 6) {//si le déplacement est de bas vers le haut
            for (Player player : this._players) {//pour tous les joueurs
                if (player.getPosX() == posX && player.getPosX() != 0) { //si le joueur est déplacé et qu'il N'EST PAS tout en haut
                    player.moveTo(this.getCurrentPlayer().getPosX(), this.getCurrentPlayer().getPosY() + 1);
                } else {//le joueur est tout en haut, il retourne tout en bas
                    player.moveTo(this.getCurrentPlayer().getPosX(), 6);
                }
            }
        }
        // Mise à jour de la vue (éviter la redondance dans chaque if-else du code)
        this.notifyUpdatePlayerPosition(1,2);
    }

    //////////////////////////////////
    /// FONCTIONS DE VERIFICATIONS ///
    //////////////////////////////////
    public void checkGoal(){
        if(getCurrentPlayer().getCurrentGoal() == this._gameBoard.getSpecificTile(getCurrentPlayer().getPosX(), getCurrentPlayer().getPosY()).getEntity()){
            getCurrentPlayer().removeCurrentGoal();
            //notify
        }
    }

    public void checkWinner(){
        if(getCurrentPlayer().getCurrentGoal() == null){
            switch (_playerTurn){
                case 0: if(getCurrentPlayer().getPosY() == 0 && getCurrentPlayer().getPosX() == 0){ //joueur un, coin en haut à gauche
                    //notifyObserverAffichage();
                }
                case 1: if(getCurrentPlayer().getPosY() == 0 && getCurrentPlayer().getPosX() == 6){ //joueur deux, coin en haut à droite
                    //notifyObserverAffichage();
                }
                case 2: if(getCurrentPlayer().getPosY() == 6 && getCurrentPlayer().getPosX() == 0){ //joueur trois, coin en bas à gauche
                    //notifyObserverAffichage();
                }
                case 3: if(getCurrentPlayer().getPosY() == 6 && getCurrentPlayer().getPosX() == 6){ //joueur quatre, coin en bas à droite
                    //notifyObserverAffichage();
                }
                default:
            }
        }
    }

    ///////////////////////////
    /// FONCTIONS DE NOTIFY ///
    ///////////////////////////
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
