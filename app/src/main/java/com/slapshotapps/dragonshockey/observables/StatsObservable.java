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
import com.slapshotapps.dragonshockey.models.PlayerStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

/**
 * Created by willmetz on 9/12/16.
 */

public class StatsObservable {

    public static Observable<List<PlayerStats>> getPlayerStats(final FirebaseDatabase database, final List<Player> players){

        return Observable.create(new Observable.OnSubscribe<List<PlayerStats>>() {
            @Override
            public void call(final Subscriber<? super List<PlayerStats>> subscriber) {

                final Query query = database.getReference(Config.GAME_STATS).orderByChild(Config.GAME_ID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //create a list of playerStats first
                        SparseArray<PlayerStats> statMap = new SparseArray<PlayerStats>();

                        for(Player player : players){
                            statMap.put(player.playerID, new PlayerStats(player.playerID, player.firstName, player.lastName));
                        }


                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            GameStats stats = snapshot.getValue(GameStats.class);

                            //iterate through all stats for the game
                            for( GameStats.Stats s : stats.gameStats){
                                PlayerStats currentStats = statMap.get(s.playerID);

                                if(currentStats != null){
                                    currentStats.assists += s.assists;
                                    currentStats.goals += s.goals;
                                    currentStats.points = currentStats.goals + currentStats.assists;
                                    currentStats.gamesPlayed += s.present?1:0;
                                }
                            }
                        }

                        if(!subscriber.isUnsubscribed()){
                            subscriber.onNext(StatsUtils.toPlayerStats(statMap));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e("Error retrieving stats");
                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });
    }

}
