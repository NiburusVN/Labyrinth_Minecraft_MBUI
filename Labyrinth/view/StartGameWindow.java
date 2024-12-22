package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartGameWindow extends JFrame {
    private GameController controller;
    private GameWindow gameWindow;  // Référence à la fenêtre de jeu

    public StartGameWindow(GameController controller) throws IOException {
        this.controller = controller;
        this.gameWindow = new GameWindow(controller);

        // Configurer la fenêtre de démarrage
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        // Panel de démarrage
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Label de titre
        JLabel titleLabel = new JLabel("Welcome In The Labyrinth !", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.CENTER);

        // Bouton "Start Game"
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startGame();  // Appeler la méthode startGame()
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(startButton, BorderLayout.SOUTH);

        // Ajouter le panel à la fenêtre
        add(panel);
        setVisible(true);
    }


    public void startGame() throws IOException {
        this.setVisible(false);
        gameWindow.setVisible(true);
        controller.play();
    }
}
