package model;

import java.awt.*;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Integer[] _position;
    private List<Entity> _goalsDeck;
    private List<Entity> _goalsReached;

    public Player() {
        this._position = new Integer[2];
        this._goalsDeck = new ArrayList<>(6);
    }

    public void addGoal(Entity goal){
        this._goalsDeck.add(goal);
    }

    public void removeCurrentGoal(){
        this._goalsDeck.removeFirst();
    }

    public Entity getCurrentGoal(){return this._goalsDeck.getFirst();}

    public List<Entity> getGoalsDeck(){return this._goalsDeck;}

    public List<Entity> getGoalsReached(){return this._goalsReached;}

    public Integer[] getPosition(){
        return this._position;
    }

    public void moveTo(Integer[] position){
        this._position[0] = position[0];
        this._position[1] = position[1];
    }
}
