package model;

import java.io.IOException;
import java.util.ArrayList;

public interface GameObserver {

    ////////////////////
    /// VIEW UPDATES ///
    ////////////////////
    abstract void updateInitGameBoard(ArrayList<TileTemplate> _gameBoardTiles) throws IOException;
    abstract void updatePlayerPosition(Integer posX, Integer posY);
    abstract void updateMoveTilesLine(Integer posX, Integer posY);
    abstract void updateGoalsDeck();
    abstract void updateGameEnded();
}
