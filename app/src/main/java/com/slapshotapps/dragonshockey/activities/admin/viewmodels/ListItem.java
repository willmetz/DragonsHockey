package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

/**
 * Created on 10/15/16.
 */

public abstract class ListItem {

    private int layoutID;

    public abstract ItemType getItemType();


    public ListItem(int layoutID){
        this.layoutID = layoutID;
    }

    public int getLayoutID(){
        return layoutID;
    }

    public enum  ItemType{
        GAME,
        ADD_GAME
    }
}
