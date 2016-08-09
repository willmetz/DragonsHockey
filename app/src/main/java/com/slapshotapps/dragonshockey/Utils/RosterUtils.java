package com.slapshotapps.dragonshockey.Utils;

import android.provider.UserDictionary;

import com.slapshotapps.dragonshockey.models.Player;

/**
 * Created by willmetz on 8/8/16.
 */

public class RosterUtils {

    public static String getFullName(Player player){
        String playerName = "";

        if(player!=null){

            playerName = player.firstName!=null ? formatName(player.firstName) : "";

            playerName += player.lastName != null ? " " + formatName(player.lastName) : "";
        }

        return playerName;
    }

    public static String getNumber(Player player){
        String number = "??";

        if(player!=null){
            number = String.valueOf(player.number);
        }

        return number;
    }

    public static String getPosition(Player player){
        String position = "Unknown";

        if(player != null && player.position != null){
            if(player.position.equalsIgnoreCase("F")){
                position = "Forward";
            }
            else if(player.position.equalsIgnoreCase("D")){
                position = "Defense";
            }
            else if(player.position.equalsIgnoreCase("G")){
                position = "Goalie";
            }
        }

        return position;
    }


    public static String formatName(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }


}
