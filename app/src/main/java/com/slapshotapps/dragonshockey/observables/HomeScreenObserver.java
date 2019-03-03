package com.slapshotapps.dragonshockey.observables;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.Utils.HomeScreenUtils;
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import java.util.Date;
import rx.Observable;
import rx.subscriptions.Subscriptions;

/**
 * An observable class for home screen contents
 */
public class HomeScreenObserver {

    public static Observable<HomeContents> getHomeScreen(final FirebaseDatabase firebaseDatabase, final SeasonSchedule schedule, final Date referenceDate) {
        return Observable.create(subscriber -> {

            //find the next game and last game IDs
            final Game nextGame =
                ScheduleUtils.getGameAfterDate(referenceDate, schedule.getAllGames());
            final Game lastGame =
                ScheduleUtils.getGameBeforeDate(referenceDate, schedule.getAllGames());

            final Query query =
                firebaseDatabase.getReference(Config.GAME_RESULTS).orderByChild(Config.GAME_ID);

            final ValueEventListener listener =
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HomeContents homeContents = new HomeContents();
                        homeContents.lastGame = lastGame;
                        homeContents.nextGame = nextGame;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            GameResult gameResult = snapshot.getValue(GameResult.class);

                            if (lastGame != null && lastGame.getGameID() == gameResult.gameID) {
                                homeContents.lastGame.setGameResult(gameResult);
                            } else if (nextGame != null && nextGame.getGameID() == gameResult.gameID) {
                                homeContents.nextGame.setGameResult(gameResult);
                            }

                            //determine if the game was a win or a loss
                            if (HomeScreenUtils.wasWin(gameResult)) {
                                homeContents.seasonRecord.wins++;
                            } else if (HomeScreenUtils.wasOvertimeLoss(gameResult)) {
                                homeContents.seasonRecord.overtimeLosses++;
                            } else if (HomeScreenUtils.wasLoss(gameResult)) {
                                homeContents.seasonRecord.losses++;
                            } else if (HomeScreenUtils.wasTie(gameResult)) {
                                homeContents.seasonRecord.ties++;
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(homeContents);
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
