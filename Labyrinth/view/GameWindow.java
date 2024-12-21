package view;
import controller.GameController;
import model.GameObserver;
import model.TileTemplate;

import javax.swing.*; //peut-être spécifier seulement les composants utilisés
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class GameWindow extends JFrame implements GameObserver {
    public GameWindow(GameController controller) {
        super("The Labyrinth");
        setSize( 500, 500 );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel BoardPanel = new JPanel();
        BoardPanel.setLayout( new GridLayout(7, 7) );

        BufferedImage tuileAngle = new ImageIO.read("/img/exempleTuiles/tuile_angle.png");
        ImageIcon tuileLine = new ImageIcon("/img/exempleTuiles/tuile_line.png");
        ImageIcon tuileIntersection = new ImageIcon("/img/exempleTuiles/tuile_T.png");


        for (int i = 0; i < 49; i++) {
            JPanel cellPanel = new JPanel();
            cellPanel.setLayout(new OverlayLayout(cellPanel));

            JLabel tuileLabel = new JLabel();

            JLabel personnageLabel = new JLabel();

            // Ajouter les labels dans le panel de la cellule
            cellPanel.add(personnageLabel); // Ajouté après pour être superposé
            cellPanel.add(tuileLabel);

            // Ajouter la cellule dans la grille
            BoardPanel.add(cellPanel);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        Panel.add( button1, constraints );
        constraints.gridx = 1;
        Panel.add( button2, constraints );
        constraints.gridx = 2;
        Panel.add( button3, constraints );

        setVisible(true);

    }

    @Override
    public void updateGameBoard() {

    }

    @Override
    public void updatePlayerPosition() {

    }

    @Override
    public void updateGoalsDeck() {

    }

    @Override
    public void updateGameEnded() {

    }
}
