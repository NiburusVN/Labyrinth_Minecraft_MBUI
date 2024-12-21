package model;

import java.awt.*;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Integer _posX;
    private Integer _posY;
    private List<Entity> _goalsDeck;

    public Player() {
        this._posX = null;
        this._posY = null;
        this._goalsDeck = new ArrayList<>(6);
    }

    public void addGoal(Entity goal){
        this._goalsDeck.add(goal);
    }

    public void removeCurrentGoal(){
        this._goalsDeck.removeFirst();
    }

    public Entity getCurrentGoal(){return this._goalsDeck.getFirst();}

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
