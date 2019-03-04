package com.slapshotapps.dragonshockey.observables;

import android.util.SparseArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.Utils.StatsUtils;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerPosition;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

/**
 * Created by willmetz on 9/12/16.
 */

public class StatsObserver {

    public static Observable<List<PlayerStats>> getPlayerStats(final FirebaseDatabase database, final List<Player> players) {

        return Observable.create(subscriber -> {

            final Query query =
                database.getReference(Config.GAME_STATS).orderByChild(Config.GAME_ID);

            final ValueEventListener valueEventListener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //create a list of playerStats first
                        SparseArray<PlayerStats> statMap = new SparseArray<PlayerStats>();

                        for (Player player : players) {
                            statMap.put(player.playerID,
                                new PlayerStats(player.playerID, player.firstName,
                                    player.lastName, player.getPosition()));
                        }

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GameStats stats = snapshot.getValue(GameStats.class);

                            //populate the internal list of players details per game
                            stats.setGameStats(new ArrayList<GameStats.Stats>());
                            for (DataSnapshot childListSnapshot : snapshot.child("stats")
                                .getChildren()) {
                                GameStats.Stats playerGameStats =
                                    childListSnapshot.getValue(GameStats.Stats.class);

                                //get the players current stat info
                                PlayerStats currentStats = statMap.get(
                                    playerGameStats.getPlayerID());

                                //update the stat info based on the game results
                                if (currentStats != null) {
                                    currentStats.assists += playerGameStats.getAssists();
                                    currentStats.goals += playerGameStats.getGoals();
                                    currentStats.points = currentStats.goals + currentStats.assists;
                                    currentStats.gamesPlayed += playerGameStats.getPresent() ? 1 : 0;
                                    currentStats.penaltyMinutes +=
                                        playerGameStats.getPenaltyMinutes();

                                    if (currentStats.position == PlayerPosition.GOALIE) {
                                        currentStats.goalsAgainst +=
                                            playerGameStats.getGoalsAgainst();
                                        currentStats.shutouts +=
                                            playerGameStats.getGoalsAgainst()
                                                == 0 && playerGameStats.getPresent() ? 1 : 0;
                                    }
                                }
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(StatsUtils.toPlayerStats(statMap));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e("Error retrieving stats");
                    }
                });

            //remove the subscriber when canceled
            subscriber.add(
                Subscriptions.create(() -> query.removeEventListener(valueEventListener)));
        });
    }
}
