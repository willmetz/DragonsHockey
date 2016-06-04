package com.slapshotapps.dragonshockey.observables;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.Utils.ScheduleUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.models.HomeContents;

import java.util.Date;

import rx.Observable;
import rx.Subscriber;

/**
 * A custom class to help with observable code.
 */
public class ScheduleObserver
{
    public static Observable<SeasonSchedule> getHockeySchedule(final FirebaseDatabase firebaseDatabase)
    {
        return Observable.create( new Observable.OnSubscribe<SeasonSchedule>(){
            @Override
            public void call(final Subscriber<? super SeasonSchedule> subscriber)
            {
                DatabaseReference ref = firebaseDatabase.getReference(Config.GAMES);

                ref.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        SeasonSchedule schedule = new SeasonSchedule();

                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            schedule.addGame(snapshot.getValue(Game.class));
                        }

                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onNext( schedule );
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Log.e("tag", "Error retrieving data");
                    }
                });
            }
        });
    }

    public static Observable<HomeContents> getHomeScreenContents( final SeasonSchedule schedule )
    {
        return Observable.create(new Observable.OnSubscribe<HomeContents>()
        {
            @Override
            public void call(Subscriber<? super HomeContents> subscriber) {

                HomeContents homeContents = new HomeContents();

                Date currentDate = new Date();
                homeContents.lastGame = ScheduleUtils.getGameBeforeDate(currentDate, schedule.getAllGames());
                homeContents.nextGame = ScheduleUtils.getGameAfterDate(currentDate, schedule.getAllGames());

                if(!subscriber.isUnsubscribed()) {
                    subscriber.onNext( homeContents );
                    subscriber.onCompleted();
                }
            }
        });
    }
}
