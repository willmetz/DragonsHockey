package com.slapshotapps.dragonshockey.observables;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.models.HomeContents;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A custom class to help with observable code.
 */
public class ScheduleObserver {
  public static Observable<SeasonSchedule> getHockeySchedule(
      final FirebaseDatabase firebaseDatabase) {
    return Observable.create(new Observable.OnSubscribe<SeasonSchedule>() {
      @Override public void call(final Subscriber<? super SeasonSchedule> subscriber) {
        Query query = firebaseDatabase.getReference(Config.GAMES).orderByChild("gameID");

        query.addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            SeasonSchedule schedule = new SeasonSchedule();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              schedule.addGame(snapshot.getValue(Game.class));
            }

            if (!subscriber.isUnsubscribed()) {
              subscriber.onNext(schedule);
              subscriber.onCompleted();
            }
          }

          @Override public void onCancelled(DatabaseError databaseError) {
            Log.e("tag", "Error retrieving data");
          }
        });
      }
    });
  }

  public static Observable<HomeContents> getHomeScreen(final FirebaseDatabase firebaseDatabase,
      final SeasonSchedule schedule, final Date referenceDate) {
    return Observable.create(new Observable.OnSubscribe<HomeContents>() {
      @Override public void call(final Subscriber<? super HomeContents> subscriber) {

        //find the next game and last game IDs
        final Game nextGame = ScheduleUtils.getGameAfterDate(referenceDate, schedule.getAllGames());
        final Game lastGame =
            ScheduleUtils.getGameBeforeDate(referenceDate, schedule.getAllGames());

        Query query = firebaseDatabase.getReference(Config.GAME_RESULTS).orderByChild("gameID");
        if (nextGame != null && lastGame != null) {
          query.endAt(nextGame.gameID).startAt(lastGame.gameID);
        } else if (nextGame != null) {
          query.equalTo(nextGame.gameID);
        } else if (lastGame != null) {
          query.equalTo(lastGame.gameID);
        } else {
          subscriber.onError(new Throwable("No games found"));
        }

        query.addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {

            HomeContents homeContents = new HomeContents();
            homeContents.lastGame = lastGame;
            homeContents.nextGame = nextGame;

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

              GameResult gameResult = snapshot.getValue(GameResult.class);

              if (lastGame != null && lastGame.gameID == gameResult.gameID) {
                homeContents.lastGame.gameResult = gameResult;
              } else if (nextGame != null && nextGame.gameID == gameResult.gameID) {
                homeContents.nextGame.gameResult = gameResult;
              }
            }

            if (!subscriber.isUnsubscribed()) {
              subscriber.onNext(homeContents);
              subscriber.unsubscribe();
            }
          }

          @Override public void onCancelled(DatabaseError databaseError) {

          }
        });
      }
    });
  }

  public static Observable<SeasonSchedule> getScheduleWithResults(
      final FirebaseDatabase firebaseDatabase, final SeasonSchedule schedule) {
    return Observable.create(new Observable.OnSubscribe<SeasonSchedule>() {
      @Override public void call(final Subscriber<? super SeasonSchedule> subscriber) {
        Query query = firebaseDatabase.getReference(Config.GAME_RESULTS).orderByChild("gameID");

        query.addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              GameResult gameResult = snapshot.getValue(GameResult.class);

              if (gameResult != null) {
                Game game = schedule.getGame(gameResult.gameID);

                if (game != null) {
                  game.gameResult = gameResult;
                }
              }
            }

            if (!subscriber.isUnsubscribed()) {
              subscriber.onNext(schedule);
              subscriber.unsubscribe();
            }
          }

          @Override public void onCancelled(DatabaseError databaseError) {

          }
        });
      }
    });
  }
}
