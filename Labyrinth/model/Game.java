package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Game{

    //Attributs
    private Integer _playerTurn;
    private ArrayList<Entity> _valableGoals;
    private ArrayList<Player> _players;
    private GameBoard _gameBoard;
    private Integer[] _blockedInsert = new Integer[2];
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
    public void startGame() throws IOException {
        this.initGameBoard();//terrain
        this.initGoals();//objectifs sur terrain
        printGameBoard(this._gameBoard.getGameBoardTiles());
        this.notifyUpdateInitGameBoard(this._gameBoard.getGameBoardTiles());

        this.distributePlayersGoals();//objectifs des joueurs

        this.initPlayersPosition();//position des joueurs
    }

    //fonction qui crée les tuiles pour le terrain
    public void initGameBoard() throws IOException {
        this._gameBoard.initBoard(this._tileFactory, new ArrayList<>(this._valableGoals.subList(24, 28)));
    }

    public void initGoals(){this._gameBoard.goalsDistribution(new ArrayList<>(this._valableGoals.subList(0,24)));}

    //fonction pour assigner les objectifs aux joueurs
    public void distributePlayersGoals() throws IOException {
        List<Entity> goals = new ArrayList<>(this._valableGoals.subList(0,24));
        for (Player player : this._players){
            Collections.shuffle(goals);
            for(int i = 0; i < 6; i++){
                player.addGoal(goals.getFirst());
                goals.removeFirst();
            }
        }

        List<Entity>[] playersGoals = new List[4]; // Java n'autorise pas les tableaux génériques directement

        playersGoals[0] = this._players.get(0).getGoalsDeck();
        playersGoals[1] = this._players.get(1).getGoalsDeck();
        playersGoals[2] = this._players.get(2).getGoalsDeck();
        playersGoals[3] = this._players.get(3).getGoalsDeck();

        this.notifyUpdateInitPlayersGoals(playersGoals);
    }

    public void initPlayersPosition() throws IOException {
        for (int i = 0; i < 4; i++){
            switch(i){
                case 0: this._players.get(i).moveTo(new Integer[]{1,1});break;
                case 1: this._players.get(i).moveTo(new Integer[]{1,7});break;
                case 2: this._players.get(i).moveTo(new Integer[]{7,1});break;
                case 3: this._players.get(i).moveTo(new Integer[]{7,7});break;
            }

        }
        //this.notifyUpdatePlayerPosition(playersMoved, this._gameBoard.getGameBoardTiles());

        for(Integer i = 0; i < 4; i++){
            this.notifyUpdatePlayerPosition(i, null, this._players.get(i).getPosition(), null, this._gameBoard.getSpecificTile(this._players.get(i).getPosition()), this._players);
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
    public void movePlayer(Integer[] position) throws IOException {

        //Tuile cible
        Integer[] posTuile = new Integer[]{
                this.getCurrentPlayer().getPosition()[0]+position[0], //pos Vertical
                this.getCurrentPlayer().getPosition()[1]+position[1] //pos Horizontal
        };

        if((posTuile[0])>0 &&
        (posTuile[0])<8 &&
        (posTuile[1])>0 &&
        (posTuile[1])<8){ //vérification d'un déplacement autorisé

            Integer[] posJoueur = this.getCurrentPlayer().getPosition();

            ////////////////////////////////////////
            /// 0 ouest ; 1 nord ; 2 est ; 3 sud ///
            ////////////////////////////////////////

            if (position[0] == -1){ //déplacement vers le nord
                if(this._gameBoard.getSpecificTile(posJoueur).getEntries().get(1) &&
                this._gameBoard.getSpecificTile(posTuile).getEntries().get(3)){ // Vérification d'un passage valide

                    this.getCurrentPlayer().moveTo(posTuile); // déplacement vers la tuile

                    this.notifyUpdatePlayerPosition(this._playerTurn, posJoueur, posTuile, this._gameBoard.getSpecificTile(posJoueur), this._gameBoard.getSpecificTile(posTuile), this._players);

                    //this.notifyUpdatePlayerPosition(playersMoved, this._gameBoard.getGameBoardTiles());
                }
            }
            else if (position[0] == 1) { //déplacement vers le sud
                if (this._gameBoard.getSpecificTile(posJoueur).getEntries().get(3) &&
                        this._gameBoard.getSpecificTile(posTuile).getEntries().get(1)) { // Vérification d'un passage valide

                    this.getCurrentPlayer().moveTo(posTuile); // déplacement vers la tuile

                    this.notifyUpdatePlayerPosition(this._playerTurn, posJoueur, posTuile, this._gameBoard.getSpecificTile(posJoueur), this._gameBoard.getSpecificTile(posTuile), this._players);
                }
            }
            else if (position[1] == -1){ //déplacement vers l'ouest
                if(this._gameBoard.getSpecificTile(posJoueur).getEntries().get(0) &&
                        this._gameBoard.getSpecificTile(posTuile).getEntries().get(2)){ // Vérification d'un passage valide
                    this.getCurrentPlayer().moveTo(posTuile); // déplacement vers la tuile

                    this.notifyUpdatePlayerPosition(this._playerTurn, posJoueur, posTuile, this._gameBoard.getSpecificTile(posJoueur), this._gameBoard.getSpecificTile(posTuile), this._players);
                }
            }
            else if (position[1] == 1){ //déplacement vers l'est
                if(this._gameBoard.getSpecificTile(posJoueur).getEntries().get(2) &&
                        this._gameBoard.getSpecificTile(posTuile).getEntries().get(0)){ // Vérification d'un passage valide
                    this.getCurrentPlayer().moveTo(posTuile); // déplacement vers la tuile

                    this.notifyUpdatePlayerPosition(this._playerTurn, posJoueur, posTuile, this._gameBoard.getSpecificTile(posJoueur), this._gameBoard.getSpecificTile(posTuile), this._players);
                }
            }
            else{
                System.out.println("Tuile non accessible !\n");
                //notifyMessage
            }
        }
        else{
            System.out.println("Vous allez sortir du plateau à force continuer comme ça !\n");
            //notifyMessage
        }
    }

    ////////////////////////////////
    /// FONCTIONS SUR LE TERRAIN ///
    ////////////////////////////////
    public void moveTilesLine(Integer[] insertPos) throws IOException {

        if(this._blockedInsert == insertPos){
            //notifyMessage
        }
        else {

            //déplacement des tuiles du labyrinth + rétention de l'action contraire
            this._blockedInsert = this._gameBoard.insertExtraTile();

            //Déplacement des joueurs sur la ligne décalé + vérification d'éjection en dehors du terrain
            if (insertPos[0] == 0) { //du haut vers le bas
                for (Integer i = 0; i < 4; i++) { //pour tous les joueurs
                    //Le joueur est déplacé avec le terrain
                    if (Objects.equals(this._players.get(i).getPosition()[1], insertPos[1])) {
                        Integer[] oldPosition = this._players.get(i).getPosition();

                        if (this._players.get(i).getPosition()[0] == 7) { // il est éjecté du terrain

                            this._players.get(i).moveTo(new Integer[]{1, insertPos[1]});

                        }

                        else { // il n'est pas éjecté

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0] + 1, this._players.get(i).getPosition()[1]});

                        }
                        Integer[] newPosition = this._players.get(i).getPosition();
                        this.notifyUpdatePlayerPosition(i, oldPosition, newPosition, this._gameBoard.getSpecificTile(oldPosition), this._gameBoard.getSpecificTile(newPosition), this._players);
                    }
                    //
                }
            } else if (insertPos[0] == 8) { //du bas vers le haut
                for (int i = 0; i < 4; i++) { //pour tous les joueurs
                    //Le joueur est déplacé avec le terrain
                    if (Objects.equals(this._players.get(i).getPosition()[1], insertPos[1])) {
                        Integer[] oldPosition = this._players.get(i).getPosition();

                        if (this._players.get(i).getPosition()[0] == 1) { // il est éjecté du terrain

                            this._players.get(i).moveTo(new Integer[]{7, insertPos[1]});

                        } else { // il n'est pas éjecté

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0] - 1, this._players.get(i).getPosition()[1]});

                        }
                        Integer[] newPosition = this._players.get(i).getPosition();
                        this.notifyUpdatePlayerPosition(i, oldPosition, newPosition, this._gameBoard.getSpecificTile(oldPosition), this._gameBoard.getSpecificTile(newPosition), this._players);

                    }
                }
            }

            else if (insertPos[1] == 0) { //de la gauche vers la droite
                for (int i = 0; i < 4; i++) { //pour tous les joueurs
                    //Le joueur est déplacé avec le terrain
                    if (Objects.equals(this._players.get(i).getPosition()[0], insertPos[0]) ) {
                        Integer[] oldPosition = this._players.get(i).getPosition();
                        if (this._players.get(i).getPosition()[1] == 7) { // il est éjecté du terrain

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0], 1});

                        } else { // il n'est pas éjecté

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0], this._players.get(i).getPosition()[1] + 1});

                        }
                        Integer[] newPosition = this._players.get(i).getPosition();
                        this.notifyUpdatePlayerPosition(i, oldPosition, newPosition, this._gameBoard.getSpecificTile(oldPosition), this._gameBoard.getSpecificTile(newPosition), this._players);
                    }
                }
            }

            else if (insertPos[1] == 8) { //de la droite vers la gauche
                for (int i = 0; i < 4; i++) { //pour tous les joueurs
                    //Le joueur est déplacé avec le terrain
                    if (Objects.equals(this._players.get(i).getPosition()[0], insertPos[0])) {
                        Integer[] oldPosition = this._players.get(i).getPosition();

                        if (this._players.get(i).getPosition()[1] == 1) { // il est éjecté du terrain

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0], 7});

                        } else { // il n'est pas éjecté

                            this._players.get(i).moveTo(new Integer[]{this._players.get(i).getPosition()[0], this._players.get(i).getPosition()[1] - 1});

                        }
                        Integer[] newPosition = this._players.get(i).getPosition();
                        this.notifyUpdatePlayerPosition(i, oldPosition, newPosition, this._gameBoard.getSpecificTile(oldPosition), this._gameBoard.getSpecificTile(newPosition), this._players);
                    }
                }
            }
            //notifyInsertedTile

        }
    }

    public TileTemplate showExtraTile(){return _gameBoard.getExtraTile();}

    public void rotateExtraTile(Boolean clockwise){this._gameBoard.rotateExtraTile(clockwise);}

    public void moveExtraTile(Boolean clockwise){this._gameBoard.moveExtraTile(clockwise);}
    //////////////////////////////////
    /// FONCTIONS DE VERIFICATIONS ///
    //////////////////////////////////
    public void checkGoal(){
        if(getCurrentPlayer().getCurrentGoal() == this._gameBoard.getSpecificTile(getCurrentPlayer().getPosition()).getEntity()){
            getCurrentPlayer().removeCurrentGoal();
            //notifyGoal
        }
    }

    public void checkWinner(){
        if(getCurrentPlayer().getCurrentGoal() == null){
            boolean Win = false;
            switch (_playerTurn){
                case 0: Win = Arrays.equals(getCurrentPlayer().getPosition(), new Integer[]{1,1});
                break; //joueur un, coin en haut à gauche
                case 1: Win = Arrays.equals(getCurrentPlayer().getPosition(), new Integer[]{1,7});
                    break; //joueur deux, coin en haut à droite
                case 2: Win = Arrays.equals(getCurrentPlayer().getPosition(), new Integer[]{7,1});
                    break; //joueur trois, coin en bas à gauche
                case 3: Win = Arrays.equals(getCurrentPlayer().getPosition(), new Integer[]{7,7});
                    break; //joueur quatre, coin en bas à droite
                default:
                    break;
            }
            if(Win){
                //notifyObserverAffichage();
            }
        }
    }

    ///////////////////////////
    /// FONCTIONS DE NOTIFY ///
    ///////////////////////////
    public void notifyUpdateInitGameBoard(List<List<TileTemplate>> gameBoardTiles) throws IOException {
        for(GameObserver observer: this._observers){
            observer.updateInitGameBoard(gameBoardTiles);
        }
    }

    public void notifyUpdateMoveTilesLine(Integer posX, Integer posY){
        for(GameObserver observer: this._observers){
            observer.updateMoveTilesLine(posX, posY);
        }
    }

    public void notifyUpdatePlayerPosition(Integer currentNumPlayer, Integer[] oldPlayerPos, Integer[] newPlayerPos, TileTemplate oldTile, TileTemplate newTile, ArrayList<Player> playersOnTile) throws IOException {
        //Integer[][][] avec 0 = ancienne pos et 1 = nouvelle pos
        for(GameObserver observer: this._observers){
            observer.updatePlayerPosition(currentNumPlayer, oldPlayerPos, newPlayerPos, oldTile, newTile, playersOnTile);
        }
    }

    public void notifyUpdateInitPlayersGoals(List<Entity>[] playersGoals) throws IOException {
        for(GameObserver observer: this._observers){
            observer.UpdateInitPlayersGoals(playersGoals);
        }
    }

    public void printGameBoard(List<List<TileTemplate>> gameBoardTiles) {
        for (int y = 0; y < gameBoardTiles.size(); y++) {
            for (int x = 0; x < gameBoardTiles.get(y).size(); x++) {
                TileTemplate tile = gameBoardTiles.get(y).get(x);
                if (tile != null) {
                    System.out.print(tile.getType() + "\t"); // Affiche le type de la tuile
                } else {
                    System.out.print("X\t"); // Affiche X si la case est null
                }
            }
            System.out.println(); // Nouvelle ligne pour la prochaine rangée
        }
    }
}
