package com.slapshotapps.dragonshockey.observables;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.SeasonStats;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class CareerStatsObserver {

    public static Observable<List<SeasonStats>> getHistoricalStats(final FirebaseDatabase database){
        return Observable.create(new Observable.OnSubscribe<List<SeasonStats>>() {
            @Override
            public void call(final Subscriber<? super List<SeasonStats>> subscriber) {
                final Query query = database.getReference(Config.HISTORICAL_STATS);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<SeasonStats> careerStats = new ArrayList<SeasonStats>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //essentially this will just get the season id
                            SeasonStats seasonStats = snapshot.getValue(SeasonStats.class);

                            //populate the internal list of players details per game
                            seasonStats.stats = new ArrayList<GameStats>();
                            for (DataSnapshot gameSnapshot : snapshot.child("games").getChildren()) {

                                //this is essentially just the game id
                                GameStats gameStats = gameSnapshot.getValue(GameStats.class);

//                                for(DataSnapshot gameStatsSnapshot : gameSnapshot.child("stats").getChildren()){
//
//                                    //here are all the stats for the game
//                                    gameStats.gameStats.add(gameStatsSnapshot.getValue(GameStats.Stats.class));
//                                }
                                seasonStats.stats.add(gameStats);
                            }

                            careerStats.add(seasonStats);
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(careerStats);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
