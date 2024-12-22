import controller.GameController;
import model.Direction;
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
//        Integer[] extraTilePosition = new Integer[] {8, 2};
//        gameWindow.updateMoveTilesLine(extraTilePosition);
//        gameWindow.updateRotateExtraTile(Direction.WEST, extraTilePosition);
//        gameWindow.updateRotateExtraTile(Direction.EAST, extraTilePosition);
//        gameWindow.updateRotateExtraTile(Direction.SOUTH, extraTilePosition);
//        gameWindow.updateRotateExtraTile(Direction.NORTH, extraTilePosition);


    }
}
