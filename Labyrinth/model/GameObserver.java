package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface GameObserver {

    ////////////////////
    /// VIEW UPDATES ///
    ////////////////////
    abstract void updateInitGameBoard(List<List<TileTemplate>> _gameBoardTiles) throws IOException;
    abstract void updatePlayerPosition(Integer joueur, Integer[] position);
    abstract void updateMoveTilesLine(Integer posX, Integer posY);
    abstract void updateGoalsDeck();
    abstract void updateGameEnded();
}
