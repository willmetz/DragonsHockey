package com.slapshotapps.dragonshockey.models;

/**
 * A player object.
 */

public class Player {

    public static final int FORWARD = 1;
    public static final int DEFENSE = 2;
    public static final int GOALIE = 3;

    public String firstName;
    public String lastName;
    public int number;
    public int playerID;
    public int position;
    public String shot;
    public boolean injuredReserved;

    public Player(){
        //default constructor
    }

    public Player(int position){
        this.position = position;
    }

    public boolean isForward(){
        return position == FORWARD;
    }

    public boolean isDefense(){
        return position == DEFENSE;
    }

    public boolean isGoalie(){
        return position == GOALIE;
    }
}
