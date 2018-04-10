package com.slapshotapps.dragonshockey.activities.admin.viewmodels;

import com.slapshotapps.dragonshockey.models.PlayerPosition;

/**
 * Created on 10/31/16.
 */

public class PlayerStatsViewModel {

    private String playerName;
    private int goals, assists, penaltyMinutes, goalsAgainst;
    private boolean isPresent, isDirty;
    private final PlayerPosition position;
    private final int playerNumber, playerID;

    public static class PlayerStatsVMBuilder {
        private String playerName;
        private int goals, assists, playerID, playerNumber, penaltyMinutes, goalsAgainst;
        private boolean isPresent;
        private PlayerPosition position;

        public PlayerStatsVMBuilder() {

        }

        public PlayerStatsVMBuilder playerID(int playerID) {
            this.playerID = playerID;
            return this;
        }

        public PlayerStatsVMBuilder playerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public PlayerStatsVMBuilder goals(int goals) {
            this.goals = goals;
            return this;
        }

        public PlayerStatsVMBuilder assists(int assists) {
            this.assists = assists;
            return this;
        }

        public PlayerStatsVMBuilder penaltyMinutes(int penaltyMinutes) {
            this.penaltyMinutes = penaltyMinutes;
            return this;
        }

        public PlayerStatsVMBuilder playerNumber(int playerNumber) {
            this.playerNumber = playerNumber;
            return this;
        }

        public PlayerStatsVMBuilder present(boolean present) {
            this.isPresent = present;
            return this;
        }

        public PlayerStatsVMBuilder position(PlayerPosition position) {
            this.position = position;
            return this;
        }

        public PlayerStatsVMBuilder goalsAgainst(int goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
            return this;
        }

        public PlayerStatsViewModel build() {
            return new PlayerStatsViewModel(this.playerName, this.goals, this.assists,
                this.playerID, this.playerNumber, this.isPresent, this.penaltyMinutes,
                this.position, this.goalsAgainst);
        }
    }

    public PlayerStatsViewModel(String playerName, int goals, int assists,
        int playerID, int playerNumber,
        boolean isPresent, int penaltyMinutes,
        PlayerPosition position, int goalsAgainst) {
        this.playerID = playerID;
        this.assists = assists;
        this.goals = goals;
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.isPresent = isPresent;
        this.penaltyMinutes = penaltyMinutes;
        this.position = position;
        this.goalsAgainst = goalsAgainst;
        isDirty = false;
    }

    public String getPlayerName() {
        return playerName != null ? playerName : "";
    }

    public String getPlayerNumber() {
        return String.valueOf(playerNumber);
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean getPresence() {
        return isPresent;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setPresence(boolean isPresent) {
        this.isPresent = isPresent;
        isDirty = true;
    }

    public String getGoalsAgainst() {
        return String.valueOf(goalsAgainst);
    }

    public void setGoalsAgainst(String goalsAgainst) {
        if (goalsAgainst == null || goalsAgainst.isEmpty()) {
            this.goalsAgainst = 0;
        } else {
            this.goalsAgainst = Integer.valueOf(goalsAgainst);
        }

        isDirty = true;
    }

    public String getGoals() {
        return String.valueOf(goals);
    }

    public void setGoals(String goals) {
        if (goals == null || goals.isEmpty()) {
            this.goals = 0;
        } else {
            this.goals = Integer.valueOf(goals);
        }

        isDirty = true;
    }

    public String getAssists() {
        return String.valueOf(assists);
    }

    public void setAssists(String assists) {
        if (assists == null || assists.isEmpty()) {
            this.assists = 0;
        } else {
            this.assists = Integer.valueOf(assists);
        }

        isDirty = true;
    }

    public String getPenaltyMinutes() {
        return String.valueOf(penaltyMinutes);
    }

    public void setPenaltyMinutes(String penaltyMinutes) {
        if (penaltyMinutes == null || penaltyMinutes.isEmpty()) {
            this.penaltyMinutes = 0;
        } else {
            this.penaltyMinutes = Integer.valueOf(penaltyMinutes);
        }

        isDirty = true;
    }
}
