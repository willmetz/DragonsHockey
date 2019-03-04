package com.slapshotapps.dragonshockey.observables;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.activities.careerStats.CareerStatsData;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.SeasonStats;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.subscriptions.Subscriptions;

public class CareerStatsObserver {

    public static Observable<Player> getPlayer(final FirebaseDatabase database, final int playerID) {
        return Observable.create(subscriber -> {

            final Query query =
                database.getReference(Config.ROSTER).orderByChild("playerID").equalTo(playerID);
            final ValueEventListener listener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Player player = new Player();

                        for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                            player = playerSnapshot.getValue(Player.class);
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(player);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("tag", "Error retrieving data");
                    }
                });

            //when the subscription is cancelled remove the listener
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
        });
    }

    public static Observable<List<SeasonStats>> getHistoricalStats(final FirebaseDatabase database) {
        return Observable.create(subscriber -> {
            final Query query = database.getReference(Config.HISTORICAL_STATS);

            final ValueEventListener valueEventListener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<SeasonStats> careerStats = new ArrayList<SeasonStats>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //essentially this will just get the season id
                            SeasonStats seasonStats = snapshot.getValue(SeasonStats.class);

                            //get the actual game stats
                            seasonStats.setStats(new ArrayList<GameStats>());
                            for (DataSnapshot gameSnapshot : snapshot.child("games")
                                .getChildren()) {
                                seasonStats.getStats().add(gameSnapshot.getValue(GameStats.class));
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
            subscriber.add(
                Subscriptions.create(() -> query.removeEventListener(valueEventListener)));
        });
    }

    public static Observable<CareerStatsData> getCareerStatsData(final FirebaseDatabase database, final int playerID) {
        return Observable.zip(getPlayer(database, playerID), getHistoricalStats(database),
            CareerStatsData::new);
    }
}
