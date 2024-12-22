package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface GameObserver {

    ////////////////////
    /// VIEW UPDATES ///
    ////////////////////
    abstract void updateInitGameBoard(List<List<TileTemplate>> gameBoardTiles) throws IOException;
    abstract void updatePlayerPosition(Integer[][][] playersMoved, List<List<TileTemplate>> gameBoardTiles);
    abstract void updateMoveTilesLine(Integer posX, Integer posY);
    abstract void updateGoalsDeck();
    abstract void updateGameEnded();
}
