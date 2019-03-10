package com.slapshotapps.dragonshockey.Utils;

import com.slapshotapps.dragonshockey.models.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willmetz on 8/8/16.
 */

public class RosterUtils {

    public static String getFullName(Player player) {
        String playerName = "";

        if (player != null) {

            playerName = player.getFirstName() != null ? formatName(player.getFirstName()) : "";

            playerName += player.getLastName() != null ? " " + formatName(player.getLastName()) : "";
        }

        return playerName;
    }

    public static String getNumber(Player player) {
        String number = "??";

        if (player != null) {
            number = String.valueOf(player.getNumber());
        }

        return number;
    }

    public static String getPosition(Player player) {
        String position = "Unknown";

        if (player != null) {
            if (player.isForward()) {
                position = "Forward";
            } else if (player.isDefense()) {
                position = "Defense";
            } else if (player.isGoalie()) {
                position = "Goalie";
            }
        }

        return position;
    }

    public static String formatName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static ArrayList<Player> sortRoster(List<Player> roster) {

        ArrayList<Player> sortedRoster = new ArrayList<>();
        ArrayList<Player> forwards = new ArrayList<>();
        ArrayList<Player> defense = new ArrayList<>();
        ArrayList<Player> goalie = new ArrayList<>();

        for (Player player : roster) {
            if (player.isForward()) {
                forwards.add(player);
            } else if (player.isDefense()) {
                defense.add(player);
            } else if (player.isGoalie()) {
                goalie.add(player);
            }
        }

        sortedRoster.addAll(forwards);
        sortedRoster.addAll(defense);
        sortedRoster.addAll(goalie);

        return sortedRoster;
    }
}
