package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface GameObserver {

    ////////////////////
    /// VIEW UPDATES ///
    ////////////////////
    abstract void updateInitGameBoard(List<List<TileTemplate>> gameBoardTiles) throws IOException;
    abstract void updatePlayerPosition(Integer currentNumPlayer, Integer[] oldPlayerPos, Integer[] newPlayerPos, TileTemplate oldTile, TileTemplate newTile, ArrayList<Player> playersOnTile) throws IOException;
    abstract void updateMoveExtraTile(Integer posX, Integer posY);
    abstract void updateMoveTilesLine(Integer[] posExtraTile);
    abstract void UpdateInitPlayersGoals(List<Entity>[] playersGoals) throws IOException;
    abstract void updateGoalsDeck();
    abstract void updateGameEnded();
}
