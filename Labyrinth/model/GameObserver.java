package model;

public interface GameObserver {
    abstract void updateGameBoard();

    abstract void updatePlayerPosition();

    abstract void updateGoalsDeck();

    abstract void updateGameStatus();
}
