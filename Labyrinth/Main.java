import controller.GameController;
import model.Game;
import view.GameWindow;

import java.util.Random;

public class Main {
    public static void main(String args[]) throws Exception {


        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);

        Game game = new Game();
        game.addObserver(gameWindow);
        GameController gameController = new GameController(game);
        gameController.play();

    }
}
