package com.slapshotapps.dragonshockey.dialogs;

public enum StatSortSelection {
    GamesPlayed("Games Played", 0), Goals("Goals", 1), Assists("Assists", 2), Points("Points", 3),
    PenaltyMinutes("Penalty Minutes", 4);

    String name;
    int index;

    StatSortSelection(String name, int index) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}