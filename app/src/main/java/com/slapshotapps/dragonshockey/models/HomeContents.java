package com.slapshotapps.dragonshockey.models;

/**
 * A custom object for the home screen contents
 */
public class HomeContents {

    public Game lastGame;
    public Game nextGame;
    public SeasonRecord seasonRecord;

    public HomeContents(){
        seasonRecord = new SeasonRecord();
    }
}
