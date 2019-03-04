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
                        homeContents.setLastGame(lastGame);
                        homeContents.setNextGame(nextGame);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            GameResult gameResult = snapshot.getValue(GameResult.class);

                            if (lastGame != null && lastGame.getGameID() == gameResult.getGameID()) {
                                homeContents.getLastGame().setGameResult(gameResult);
                            } else if (nextGame != null && nextGame.getGameID() == gameResult.getGameID()) {
                                homeContents.getNextGame().setGameResult(gameResult);
                            }

                            //determine if the game was a win or a loss
                            if (HomeScreenUtils.wasWin(gameResult)) {
                                homeContents.getSeasonRecord()
                                    .setWins(homeContents.getSeasonRecord().getWins() + 1);
                            } else if (HomeScreenUtils.wasOvertimeLoss(gameResult)) {
                                homeContents.getSeasonRecord()
                                    .setOvertimeLosses(
                                        homeContents.getSeasonRecord().getOvertimeLosses() + 1);
                            } else if (HomeScreenUtils.wasLoss(gameResult)) {
                                homeContents.getSeasonRecord()
                                    .setLosses(homeContents.getSeasonRecord().getLosses() + 1);
                            } else if (HomeScreenUtils.wasTie(gameResult)) {
                                homeContents.getSeasonRecord()
                                    .setTies(homeContents.getSeasonRecord().getTies() + 1);
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
