package model;

import java.awt.*;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Integer _posX;
    private Integer _posY;
    private List<Entity> _goalsDeck;
    private ArrayList<GameObserver> _observers;

    public Player() {
        this._posX = null;
        this._posY = null;
        this._goalsDeck = new ArrayList<>(6);
        this._observers = new ArrayList<GameObserver>();
    }

    public void addGoal(Entity goal){
        this._goalsDeck.add(goal);
    }

    public void removeActualGoal(){
        this._goalsDeck.removeFirst();
    }

    public Integer getPosX(){
        return this._posX;
    }

    public Integer getPosY(){
        return this._posY;
    }

    public void moveTo(Integer posX, Integer posY){
        this._posX = posX;
        this._posY = posY;
    }
}
