import controller.GameController;
import model.Direction;
import model.Game;
import view.GameWindow;
import view.StartGameWindow;

import java.util.Random;

public class Main {
    public static void main(String args[]) throws Exception {

        GameController gameController = new GameController();
        StartGameWindow startGameWindow = new StartGameWindow(gameController);
        startGameWindow.setVisible(true);

    }
}
