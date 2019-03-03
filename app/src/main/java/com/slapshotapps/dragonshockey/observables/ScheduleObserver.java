package com.slapshotapps.dragonshockey.observables;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import rx.Observable;
import rx.subscriptions.Subscriptions;

/**
 * A custom class to help with observable code.
 */
public class ScheduleObserver {
    public static Observable<SeasonSchedule> getHockeySchedule(final FirebaseDatabase firebaseDatabase) {
        return Observable.create(subscriber -> {
            final Query query = firebaseDatabase.getReference(Config.GAMES).orderByChild("gameID");

            final ValueEventListener listener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SeasonSchedule schedule = new SeasonSchedule();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            schedule.addGame(snapshot.getValue(Game.class));
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(schedule);
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

    public static Observable<SeasonSchedule> getScheduleWithResults(final FirebaseDatabase firebaseDatabase, final SeasonSchedule schedule) {
        return Observable.create(subscriber -> {
            final Query query =
                firebaseDatabase.getReference(Config.GAME_RESULTS).orderByChild("gameID");

            final ValueEventListener listener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GameResult gameResult = snapshot.getValue(GameResult.class);

                            if (gameResult != null) {
                                Game game = schedule.getGame(gameResult.gameID);

                                if (game != null) {
                                    game.setGameResult(gameResult);
                                }
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(schedule);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            //when the subscription is cancelled remove the listener
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
        });
    }
}
