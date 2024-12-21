import controller.GameController;
import model.Game;
import view.GameWindow;

public class Main {
    public static void main(String[] args) {
        Game M = new Game();
        GameController C = new GameController(M);
        GameWindow V = new GameWindow(C);
        M.addObserver(V);

        //init un truc pour que tout commence (normalement le swing)
    }
}
