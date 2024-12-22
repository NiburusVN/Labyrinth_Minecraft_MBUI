package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import helpers.ImageHelper;

public class GameWindow extends JFrame implements GameObserver {

    private JLabel[][] _gridCells = new JLabel[9][9];
    // Ajouter les hotbars
    private JLabel[] _player1HotbarCells;
    private JLabel[] _player2HotbarCells;
    private JLabel[] _player3HotbarCells;
    private JLabel[] _player4HotbarCells;
    private JLabel[][] _player1GoalsCells;
    private JLabel[][] _player2GoalsCells;
    private JLabel[][] _player3GoalsCells;
    private JLabel[][] _player4GoalsCells;
    private GameController _gameController;
    private boolean _isMovePlayerMode = false;

    public GameWindow(GameController gameController) throws IOException {

        // Charger l'image de fond
        BufferedImage backgroundImage = ImageIO.read(new File("./img/Map/map.png"));



        // Configurer la fenêtre principale
        super("Labyrinth");
        this._gameController = gameController;

        this._player1HotbarCells = new JLabel[6];
        this._player2HotbarCells = new JLabel[6];
        this._player3HotbarCells = new JLabel[6];
        this._player4HotbarCells = new JLabel[6];
        this._player1GoalsCells = new JLabel[3][2];
        this._player2GoalsCells = new JLabel[3][2];
        this._player3GoalsCells = new JLabel[3][2];
        this._player4GoalsCells = new JLabel[3][2];
        gameController.addObserver(this);

        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Supprimer les bordures et barre de titre
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // JLabel contenant l'image de fond
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(null);
        add(backgroundLabel);

        // Créer et ajouter la grille

        JPanel centerGrid = createGrid(this._gridCells, 9, 9, 100, 100);

//        centerGrid.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        // Positionner la grille à (610, 190)
        centerGrid.setBounds(510, 90, 900, 900); // 9*100 pour la taille totale
        backgroundLabel.add(centerGrid);



        // Hotbar du joueur 1 (haut gauche)
        JPanel player1Hotbar = createHotbar(this._player1HotbarCells, 50, 20);
        player1Hotbar.setBounds(20, 110, 400, 50); // Position et taille fixe
        backgroundLabel.add(player1Hotbar);

        // Hotbar du joueur 2 (haut droite)
        JPanel player2Hotbar = createHotbar(this._player2HotbarCells, 50, 20);
        player2Hotbar.setBounds(1430, 110, 400, 50); // Position et taille fixe
        backgroundLabel.add(player2Hotbar);

        // Hotbar du joueur 3 (bas gauche)
        JPanel player3Hotbar = createHotbar(this._player3HotbarCells, 50, 20);
        player3Hotbar.setBounds(20, 920, 400, 50); // Position et taille fixe
        backgroundLabel.add(player3Hotbar);

        // Hotbar du joueur 4 (bas droite)
        JPanel player4Hotbar = createHotbar(this._player4HotbarCells, 50, 20);
        player4Hotbar.setBounds(1430, 920, 400, 50); // Position et taille fixe
        backgroundLabel.add(player4Hotbar);

        //Goals Book joueur 1
        JPanel player1Goals = createGoalsBook(this._player1GoalsCells, 3, 2, 50, 20);

        player1Goals.setBounds(105, 220, 120, 150); // 9*100 pour la taille totale
        backgroundLabel.add(player1Goals);

        //Goals Book joueur 2
        JPanel player2Goals = createGoalsBook(this._player2GoalsCells, 3, 2, 50, 20);

        player2Goals.setBounds(1695, 220, 120, 150); // 9*100 pour la taille totale
        backgroundLabel.add(player2Goals);


        //Goals Book joueur 3
        JPanel player3Goals = createGoalsBook(this._player3GoalsCells, 3, 2, 50, 20);

        player3Goals.setBounds(105, 730, 120, 150); // 9*100 pour la taille totale
        backgroundLabel.add(player3Goals);


        //Goals Book joueur 4
        JPanel player4Goals = createGoalsBook(this._player4GoalsCells, 3, 2, 50, 20);

        player4Goals.setBounds(1695, 730, 120, 150); // 9*100 pour la taille totale
        backgroundLabel.add(player4Goals);


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if(_isMovePlayerMode) {
                    // Assigner des actions aux touches
                    if (keyCode == KeyEvent.VK_Z) {

                        try {
                            _gameController.movePlayerUp();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (keyCode == KeyEvent.VK_S) {

                        try {
                            _gameController.movePlayerDown();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (keyCode == KeyEvent.VK_Q) {

                        try {
                            _gameController.movePlayerLeft();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (keyCode == KeyEvent.VK_D) {
                        try {
                            _gameController.movePlayerRight();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (keyCode == KeyEvent.VK_ENTER) {
                        _gameController.validatePlayerPosition();
                        _isMovePlayerMode = false;
                    }
                }
                else{
                     if (keyCode == KeyEvent.VK_ENTER) {
                         _isMovePlayerMode = true;
                         try {
                             _gameController.validatePuttingExtraTile();
                         } catch (IOException ex) {
                             throw new RuntimeException(ex);
                         }
                     }
                     else if(keyCode == KeyEvent.VK_E){
                         _gameController.rotateExtraTile(true);
                     }

                    else if(keyCode == KeyEvent.VK_A){
                        _gameController.rotateExtraTile(false);
                    }
                    else if (keyCode == KeyEvent.VK_D){
                        _gameController.moveExtraTile(true);
                     }
                    else if (keyCode == KeyEvent.VK_Q) {
                        _gameController.moveExtraTile(false);
                     }
                }
            }
        });


        setVisible(true);

    }

    private static JPanel createGrid(JLabel[][] gridCells, int rows, int cols, int cellWidth, int cellHeight) {
        JPanel grid = new JPanel(new GridLayout(rows, cols));
        grid.setOpaque(false); // Permet de voir l'image de fond

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JLabel cell = new JLabel();
                cell.setPreferredSize(new Dimension(cellWidth, cellHeight));
                //cell.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure pour debug
                gridCells[row][col] = cell;
                grid.add(cell);
            }
        }
        return grid;
    }

    private static JPanel createGoalsBook(JLabel[][] goalsCells, int rows, int cols, int slotSize, int spacing) {
        JPanel goalsBook = new JPanel(new GridLayout(rows, cols, spacing, 0));
        goalsBook.setOpaque(false); // Permet de voir l'image de fond

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JLabel slot = new JLabel();
                slot.setPreferredSize(new Dimension(slotSize, slotSize));
                //slot.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure pour debug
                goalsCells[row][col] = slot;
                goalsBook.add(slot);
            }
        }
        return goalsBook;
    }

    private static JPanel createHotbar(JLabel[] hotbarCells, int slotSize, int spacing) {
        JPanel hotbar = new JPanel(new GridLayout(1, hotbarCells.length, spacing, 0));
        hotbar.setOpaque(false); // Permet de voir l'image de fond

        for (int i = 0; i < hotbarCells.length; i++) {
            JLabel slot = new JLabel();
            slot.setPreferredSize(new Dimension(slotSize, slotSize));
            //slot.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure des slots pour debug
            hotbarCells[i] = slot;
            hotbar.add(slot);
        }

        return hotbar;
    }

    private static void setImageInCell(JLabel cell, BufferedImage image) {
        int cellWidth = cell.getPreferredSize().width;
        int cellHeight = cell.getPreferredSize().height;

        // Redimensionner l'image pour qu'elle s'adapte à la cellule
        Image scaledImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);

        // Ajouter l'image au JLabel
        cell.setIcon(new ImageIcon(scaledImage));
    }

    @Override
    public void updateInitGameBoard(List<List<TileTemplate>> gameBoardTiles) throws IOException {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                TileTemplate tile = gameBoardTiles.get(y).get(x);
                if (tile != null) {
                    BufferedImage imageTile;
                    int orientation = switch (tile.getOrientation()) {
                        case Direction.WEST -> -90;
                        case Direction.NORTH -> 0;
                        case Direction.EAST -> 90;
                        case Direction.SOUTH -> 180;
                    };

                    // Charger image de la tuile
                    String type = tile.getType();
                    String pathBufferedImageTile = "./img/Map/tiles/" + type + ".png";
                    imageTile = ImageIO.read(new File(pathBufferedImageTile));

                    // rotation de la tuile
                    imageTile = ImageHelper.rotate(imageTile, Math.toRadians(orientation));

                    // vérifie l'entité si elle existe
                    if (tile.getEntity() != null) {
                        String[][] pathBufferedImageEntity = new String[1][5];
                        if(!Objects.equals(tile.getType(), "StartingTile")) {
                            pathBufferedImageEntity[0][0] = "./img/goals/" + tile.getEntity().toString() + ".png";
                        }

                        else{
                            pathBufferedImageEntity[0][0] = "./img/players/test.jpg";
                        }
                        pathBufferedImageEntity[0][1] = "25";
                        pathBufferedImageEntity[0][2] = "25";
                        pathBufferedImageEntity[0][3] = "50";
                        pathBufferedImageEntity[0][4] = "50";
                        imageTile = ImageHelper.merge(imageTile, pathBufferedImageEntity);

                    }

                    else{
                        imageTile = ImageIO.read(new File(pathBufferedImageTile));
                    }

                    // Mise à jour la cellule de la grille
                    setImageInCell(this._gridCells[y][x], imageTile);
                }
            }
        }

    }


    @Override
    public void updatePlayerPosition(Integer currentNumPlayer, Integer[] oldPlayerPos, Integer[] newPlayerPos, TileTemplate oldTile, TileTemplate newTile, ArrayList<Player> playersOnTile) throws IOException {

            // Ancienne position
            if (oldPlayerPos != null && oldTile != null) {

                String oldTileType = oldTile.getType();
                Entity oldTileEntity = oldTile.getEntity();

                String[][] pathBufferedImageOnTile = new String[5][5];
                int oldTileOrientation = switch (oldTile.getOrientation()) {
                    case Direction.WEST -> -90;
                    case Direction.NORTH -> 0;
                    case Direction.EAST -> 90;
                    case Direction.SOUTH -> 180;
                };

                if (oldTileEntity != null) {
                    pathBufferedImageOnTile[0][0] = "./img/goals/" + oldTileEntity.toString() + ".png";
                    pathBufferedImageOnTile[0][1] = "25";
                    pathBufferedImageOnTile[0][2] = "25";
                    pathBufferedImageOnTile[0][3] = "50";
                    pathBufferedImageOnTile[0][4] = "50";
                } else {
                    pathBufferedImageOnTile[0] = null;
                }

                for (Integer i = 0; i < 4; i++) {
                    if (playersOnTile.get(i).getPosition() == oldPlayerPos) {
                        pathBufferedImageOnTile[i + 1][0] = "./img/players/player" + (i + 1) + ".png";

                        if (i == 0) {
                            pathBufferedImageOnTile[i + 1][1] = "0";
                            pathBufferedImageOnTile[i + 1][2] = "0";
                        } else if (i == 1) {
                            pathBufferedImageOnTile[i + 1][1] = "100";
                            pathBufferedImageOnTile[i + 1][2] = "0";
                        } else if (i == 2) {
                            pathBufferedImageOnTile[i + 1][1] = "0";
                            pathBufferedImageOnTile[i + 1][2] = "100";
                        } else{
                            pathBufferedImageOnTile[i + 1][1] = "100";
                            pathBufferedImageOnTile[i + 1][2] = "100";
                        }

                        pathBufferedImageOnTile[i + 1][3] = "50";
                        pathBufferedImageOnTile[i + 1][4] = "50";
                    } else {
                        pathBufferedImageOnTile[i + 1] = null;
                    }
                }

                String pathBufferedImageTile = "./img/Map/tiles/" + oldTileType + ".png";

                BufferedImage imageTile = ImageIO.read(new File(pathBufferedImageTile));
                imageTile = ImageHelper.rotate(imageTile, Math.toRadians(oldTileOrientation));
                imageTile = ImageHelper.mergeExcluding(imageTile, pathBufferedImageOnTile, "./img/players/player"+(currentNumPlayer+1)+".png");

                setImageInCell(this._gridCells[oldPlayerPos[0]][oldPlayerPos[1]], imageTile);
            }


            String newTileType = newTile.getType();
            Entity newTileEntity = newTile.getEntity();


            String[][] pathBufferedImageOnTileNew = new String[5][5];
            int newTileOrientation = switch (newTile.getOrientation()) {
                case Direction.WEST -> -90;
                case Direction.NORTH -> 0;
                case Direction.EAST -> 90;
                case Direction.SOUTH -> 180;
            };

            if (newTileEntity != null) {
                if(!Objects.equals(newTileType, "StartingTile")) {
                    pathBufferedImageOnTileNew[0][0] = "./img/goals/" + newTileEntity.toString() + ".png";
                }
                else{
                    pathBufferedImageOnTileNew[0][0] = "./img/CheckPoint/" + newTileEntity.toString() + ".png";
                }
                pathBufferedImageOnTileNew[0][1] = "25";
                pathBufferedImageOnTileNew[0][2] = "25";
                pathBufferedImageOnTileNew[0][3] = "50";
                pathBufferedImageOnTileNew[0][4] = "50";
            } else {
                pathBufferedImageOnTileNew[0] = null;
            }


            for (int i = 0; i < 4; i++) {

                if (playersOnTile.get(i).getPosition()[0] == newPlayerPos[0] && playersOnTile.get(i).getPosition()[1] == newPlayerPos[1]) {
                    pathBufferedImageOnTileNew[i + 1][0] = "./img/players/player"+ (i+1) +".png";

                    if (i == 0){
                        pathBufferedImageOnTileNew[i + 1][1] = "0";
                        pathBufferedImageOnTileNew[i + 1][2] = "0";
                    }
                    else if (i == 1) {
                        pathBufferedImageOnTileNew[i + 1][1] = "50";
                        pathBufferedImageOnTileNew[i + 1][2] = "0";

                    }

                    else if (i == 2) {
                        pathBufferedImageOnTileNew[i + 1][1] = "0";
                        pathBufferedImageOnTileNew[i + 1][2] = "50";
                    }

                    else if (i == 3) {
                        pathBufferedImageOnTileNew[i + 1][1] = "50";
                        pathBufferedImageOnTileNew[i + 1][2] = "50";
                    }

                    pathBufferedImageOnTileNew[i + 1][3] = "50";
                    pathBufferedImageOnTileNew[i + 1][4] = "50";
                }
                else {
                    pathBufferedImageOnTileNew[i + 1] = null;
                }
            }

            String pathBufferedImageTileNew = "./img/Map/tiles/" + newTileType + ".png";

            String newTileImagePath = "./img/Map/tiles/" + newTileType + ".png";

            BufferedImage imageTileNew = ImageIO.read(new File(newTileImagePath));
            imageTileNew = ImageHelper.rotate(imageTileNew, Math.toRadians(newTileOrientation));
            imageTileNew = ImageHelper.merge(imageTileNew, pathBufferedImageOnTileNew);

            setImageInCell(this._gridCells[newPlayerPos[0]][newPlayerPos[1]], imageTileNew);

    }

    @Override
    public void updateRotateExtraTile(Direction extraTileOrientation, Integer[] extraTilePos) {
        // Déterminer l'angle de rotation en fonction de la direction
        int orientation = switch (extraTileOrientation) {
            case Direction.WEST -> -90;
            case Direction.NORTH -> 0;
            case Direction.EAST -> 90;
            case Direction.SOUTH -> 180;
        };

        // Récupérer l'image de l'icône
        ImageIcon icon = (ImageIcon) this._gridCells[extraTilePos[0]][extraTilePos[1]].getIcon();
        Image image = icon.getImage(); // Récupérer l'image contenue dans l'ImageIcon

        // Si l'image n'est pas déjà un BufferedImage, la convertir
        BufferedImage bufferedImage;

        // Créer un BufferedImage à partir de l'image
        bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Appliquer la rotation en utilisant ImageHelper
        BufferedImage rotatedImage = ImageHelper.rotate(bufferedImage, Math.toRadians(orientation));

        // Mettre à jour l'icône avec l'image tournée
        this._gridCells[extraTilePos[0]][extraTilePos[1]].setIcon(new ImageIcon(rotatedImage));

    }




    @Override
    public void updateInitPlayersGoals(List<Entity>[] playersGoals) throws IOException {

        for(int i = 0; i < playersGoals.length; i++){ // size = 4
            JLabel[][] bookPlayerGoals;
            if(i == 0){
                bookPlayerGoals = this._player1GoalsCells;
            }

            else if (i == 1) {
                bookPlayerGoals = this._player2GoalsCells;
            }

            else if (i == 2) {
                bookPlayerGoals = this._player3GoalsCells;
            }

            else{
                bookPlayerGoals = this._player4GoalsCells;
            }
            int index = 0;
            for(int y = 0; y < 3; y++){
                for(int x = 0; x < 2; x++){
                    String pathBufferedImageGoal = "./img/goals/" + playersGoals[i].get(index).toString() + ".png";
                    BufferedImage imageGoal = ImageIO.read(new File(pathBufferedImageGoal));
                    setImageInCell(bookPlayerGoals[y][x], imageGoal);
                    index++;
                }
            }
            bookPlayerGoals[0][0].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        }
    }
    @Override
    public void updateMoveExtraTile(Integer[] newPosExtraTile, Integer[] oldPosExtraTile) {
        // ça récupère l'icône de l'ancienne position
        ImageIcon icon = (ImageIcon) this._gridCells[oldPosExtraTile[0]][oldPosExtraTile[1]].getIcon();

        // Met à jour l'icône avec l'image dans la nouvelle position
        this._gridCells[newPosExtraTile[0]][newPosExtraTile[1]].setIcon(icon);

        // Retire l'image de l'ancienne position
        this._gridCells[oldPosExtraTile[0]][oldPosExtraTile[1]].setIcon(null);
        int x = oldPosExtraTile[0];
        int y = oldPosExtraTile[1];

    }


    @Override
    public void updateMoveTilesLine(Integer[] posExtraTile) {

        if (posExtraTile[0] == 0) { // du haut vers le bas
            for (int i = 0; i < 8; i++) {
                ImageIcon temp = (ImageIcon) this._gridCells[i][posExtraTile[1]].getIcon();
                this._gridCells[i][posExtraTile[1]].setIcon((ImageIcon) this._gridCells[i + 1][posExtraTile[1]].getIcon());
                this._gridCells[i + 1][posExtraTile[1]].setIcon(temp);
            }

        } else if (posExtraTile[1] == 8) { // de la droite vers la gauche
            for (int i = 0; i < 8; i++) {
                ImageIcon temp = (ImageIcon) this._gridCells[posExtraTile[0]][8 - i].getIcon();
                this._gridCells[posExtraTile[0]][8 - i].setIcon((ImageIcon) this._gridCells[posExtraTile[0]][7 - i].getIcon());
                this._gridCells[posExtraTile[0]][7 - i].setIcon(temp);
            }

        } else if (posExtraTile[0] == 8) { // du bas vers le haut
            for (int i = 0; i < 8; i++) {
                ImageIcon temp = (ImageIcon) this._gridCells[8 - i][posExtraTile[1]].getIcon();
                this._gridCells[8 - i][posExtraTile[1]].setIcon((ImageIcon) this._gridCells[7 - i][posExtraTile[1]].getIcon());
                this._gridCells[7 - i][posExtraTile[1]].setIcon(temp);
            }

        } else if (posExtraTile[1] == 0) { // de la gauche vers la droite
            for (int i = 0; i < 8; i++) {
                ImageIcon temp = (ImageIcon) this._gridCells[posExtraTile[0]][i].getIcon();
                this._gridCells[posExtraTile[0]][i].setIcon((ImageIcon) this._gridCells[posExtraTile[0]][i + 1].getIcon());
                this._gridCells[posExtraTile[0]][i + 1].setIcon(temp);
            }
        }
    }

    @Override
    public void updateGoalsDeck(Integer numCurrentPlayer, Integer[] posGoal) {
        JLabel[][] goalsCells = null;
        JLabel[] hotBar = null;

        this._gridCells[posGoal[0]][posGoal[1]].setIcon(null);

        // Initialisation des tableaux en fonction du joueur
        switch (numCurrentPlayer) {
            case 0 -> { goalsCells = this._player1GoalsCells; hotBar = this._player1HotbarCells; }
            case 1 -> { goalsCells = this._player2GoalsCells; hotBar = this._player2HotbarCells; }
            case 2 -> { goalsCells = this._player3GoalsCells; hotBar = this._player3HotbarCells; }
            default -> { goalsCells = this._player4GoalsCells; hotBar = this._player4HotbarCells; }
        }

        boolean iconMoved = false;  // Flag pour savoir si une icône a été déplacée
        for (int i = 0; i < goalsCells.length && !iconMoved; i++) {
            for (int j = 0; j < goalsCells[i].length && !iconMoved; j++) {
                if (goalsCells[i][j].getIcon() != null) {
                    // Déplacer l'icône vers la hotbar
                    for (int k = 0; k < hotBar.length; k++) {
                        if (hotBar[k].getIcon() == null) {
                            hotBar[k].setIcon(goalsCells[i][j].getIcon());
                            goalsCells[i][j].setIcon(null);

                            // Supprimer la bordure rouge de l'ancienne position
                            //goalsCells[i][j].setBorder(null);

                            // Déplacer la bordure rouge vers le suivant dans goalsCells
                            boolean moved = false;
                            for (int nextRow = i; nextRow < goalsCells.length && !moved; nextRow++) {
                                for (int nextCol = (nextRow == i ? j + 1 : 0); nextCol < goalsCells[nextRow].length; nextCol++) {
                                    if (goalsCells[nextRow][nextCol].getIcon() != null) {
                                        //[nextRow][nextCol].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                                        moved = true; // Indiquer que la bordure a été déplacée
                                        iconMoved = true; // Terminer la recherche
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateNextPlaySamePlayer() throws IOException {

    }


    @Override
    public void updateGameEnded(Integer numCurrentPlayer) {
        this.setVisible(false);  // Cache la vue actuelle (par exemple GameWindow)

        JFrame endingWindow = new JFrame("Fin de Partie");
        endingWindow.setSize(1920, 1080);  // Taille de la fenêtre
        endingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fermeture de l'application lors de la fermeture de la fenêtre

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Créer le label avec le message
        JLabel resultLabel = new JLabel("Le joueur " + String.valueOf(numCurrentPlayer+1) + " a remporté la partie !", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 50));
        resultLabel.setForeground(Color.WHITE);
        panel.add(resultLabel, BorderLayout.CENTER);

        // Ajouter le panel à la fenêtre
        endingWindow.add(panel);
        endingWindow.setLocationRelativeTo(null);  // Centrer la fenêtre à l'écran
        endingWindow.setVisible(true);  // Afficher la nouvelle fenêtre avec le messag
    }


}
