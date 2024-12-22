package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
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
    private JLabel[] _player1HotbarCells = new JLabel[6];
    private JLabel[] _player2HotbarCells = new JLabel[6];
    private JLabel[] _player3HotbarCells = new JLabel[6];
    private JLabel[] _player4HotbarCells = new JLabel[6];
    private JLabel[][] _player1GoalsCells = new JLabel[3][2];
    private JLabel[][] _player2GoalsCells = new JLabel[3][2];
    private JLabel[][] _player3GoalsCells = new JLabel[3][2];
    private JLabel[][] _player4GoalsCells = new JLabel[3][2];
    private JLabel backgroundLabel;

    public GameWindow() throws IOException {
        // Charger l'image de fond
        BufferedImage backgroundImage = ImageIO.read(new File("./img/Map/map.png"));

        // Configurer la fenêtre principale
        super("Labyrinth");
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

        setVisible(true);

    }

    private static JPanel createGrid(JLabel[][] gridCells, int rows, int cols, int cellWidth, int cellHeight) {
        JPanel grid = new JPanel(new GridLayout(rows, cols));
        grid.setOpaque(false); // Permet de voir l'image de fond

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JLabel cell = new JLabel();
                cell.setPreferredSize(new Dimension(cellWidth, cellHeight));
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure pour debug
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
                slot.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure pour debug
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
            slot.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure des slots pour debug
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
                    System.out.println(tile.getType().toString());
                    System.out.println(x);
                    System.out.println(y);
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
                        System.out.println(tile.getEntity().toString());
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
        try {
            // Ancienne position
            if (oldPlayerPos != null && oldTile != null) {
                System.out.println("Processing old position: " + oldPlayerPos[0] + ", " + oldPlayerPos[1]);

                String oldTileType = oldTile.getType();
                Entity oldTileEntity = oldTile.getEntity();
                System.out.println("Old Tile Type: " + oldTileType);
                System.out.println("Old Tile Entity: " + (oldTileEntity != null ? oldTileEntity : "No Entity"));

                String[][] pathBufferedImageOnTile = new String[5][5];
                int oldTileOrientation = switch (oldTile.getOrientation()) {
                    case Direction.WEST -> -90;
                    case Direction.NORTH -> 0;
                    case Direction.EAST -> 90;
                    case Direction.SOUTH -> 180;
                };
                System.out.println("Old Tile Orientation: " + oldTileOrientation);

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
                    if (!i.equals(currentNumPlayer) && playersOnTile.get(i).getPosition() == oldPlayerPos) {
                        pathBufferedImageOnTile[i + 1][0] = "./img/players/player" + (i + 1) + ".png";
                        System.out.println("Old Player Image Path: " + pathBufferedImageOnTile[i + 1][0]);

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
                System.out.println("Old Tile Image Path: " + pathBufferedImageTile);

                BufferedImage imageTile = ImageIO.read(new File(pathBufferedImageTile));
                imageTile = ImageHelper.rotate(imageTile, Math.toRadians(oldTileOrientation));
                imageTile = ImageHelper.merge(imageTile, pathBufferedImageOnTile);

                setImageInCell(this._gridCells[oldPlayerPos[0]][oldPlayerPos[1]], imageTile);
            }

            // Nouvelle position
            System.out.println("Processing new position: " + newPlayerPos[0] + ", " + newPlayerPos[1]);

            String newTileType = newTile.getType();
            Entity newTileEntity = newTile.getEntity();
            System.out.println("New Tile Type: " + newTileType);
            System.out.println("New Tile Entity: " + (newTileEntity != null ? newTileEntity : "No Entity"));

            String[][] pathBufferedImageOnTileNew = new String[5][5];
            int newTileOrientation = switch (newTile.getOrientation()) {
                case Direction.WEST -> -90;
                case Direction.NORTH -> 0;
                case Direction.EAST -> 90;
                case Direction.SOUTH -> 180;
            };
            System.out.println("New Tile Orientation: " + newTileOrientation);

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
                System.out.println("New Entity Image Path: EZZZZZZZZZZZZZZZ" + pathBufferedImageOnTileNew[0][0]);
            } else {
                pathBufferedImageOnTileNew[0] = null;
            }



            for (int i = 0; i < 4; i++) {
                System.out.println("Position Joueur actuelle : x : " + playersOnTile.get(i).getPosition()[0] + " y :" + playersOnTile.get(i).getPosition()[1]);
                System.out.println("new position compare: " + newPlayerPos[0] + ", " + newPlayerPos[1]);
                if (playersOnTile.get(i).getPosition()[0] == newPlayerPos[0] && playersOnTile.get(i).getPosition()[1] == newPlayerPos[1]) {
                    pathBufferedImageOnTileNew[i + 1][0] = "./img/players/player"+ (i+1) +".png";
                    System.out.println("New Player Image Path: " + pathBufferedImageOnTileNew[i + 1][0]);

                    System.out.println("WESSHHH" + i);

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
            System.out.println("New Tile Image Path: " + pathBufferedImageTileNew);

//            BufferedImage imageTileNew = ImageIO.read(new File(pathBufferedImageTileNew));



            String newTileImagePath = "./img/Map/tiles/" + newTileType + ".png";

            BufferedImage imageTileNew = ImageIO.read(new File(newTileImagePath));
            imageTileNew = ImageHelper.rotate(imageTileNew, Math.toRadians(newTileOrientation));
            imageTileNew = ImageHelper.merge(imageTileNew, pathBufferedImageOnTileNew);

            setImageInCell(this._gridCells[newPlayerPos[0]][newPlayerPos[1]], imageTileNew);




        } catch (Exception e) {
            System.err.println("Error updating player position: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public void UpdateInitPlayersGoals(List<Entity>[] playersGoals) throws IOException {

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
    public void updateMoveTilesLine(Integer posX, Integer posY) {}

    @Override
    public void updateGoalsDeck() {}

    @Override
    public void updateGameEnded() {}
}
