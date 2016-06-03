package com.slapshotapps.dragonshockey.observables;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.Utils.ScheduleHelpers;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * A custom class to help with observable code.
 */
public class ScheduleObserver
{
    public static Observable<DataSnapshot> getGamesObservable(final FirebaseDatabase firebaseDatabase)
    {
        return Observable.create( new Observable.OnSubscribe<DataSnapshot>(){
            @Override
            public void call(final Subscriber<? super DataSnapshot> subscriber)
            {
                DatabaseReference ref = firebaseDatabase.getReference(Config.GAMES);

                ref.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if(!subscriber.isUnsubscribed()) {
                            subscriber.onNext(dataSnapshot);
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

    public static Observable<List<Game>> getScheduleFromSnapshot( final DataSnapshot dataSnapshot)
    {
        return Observable.create( new Observable.OnSubscribe<List<Game>>(){
            @Override
            public void call(Subscriber<? super List<Game>> subscriber)
            {
                ArrayList<Game> schedule = new ArrayList<Game>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    schedule.add(snapshot.getValue(Game.class));
                }

                if(!subscriber.isUnsubscribed()) {
                    subscriber.onNext( schedule );
                    subscriber.onCompleted();
                }

            }
        });
    }

    public static Observable<HomeContents> getHomeScreenContents( final List<Game> schedule )
    {
        return Observable.create(new Observable.OnSubscribe<HomeContents>()
        {
            @Override
            public void call(Subscriber<? super HomeContents> subscriber) {

                HomeContents homeContents = new HomeContents();

                Date currentDate = new Date();
                homeContents.lastGame = ScheduleHelpers.getGameBeforeDate(currentDate, schedule);
                homeContents.nextGame = ScheduleHelpers.getGameAfterDate(currentDate, schedule);

                if(!subscriber.isUnsubscribed()) {
                    subscriber.onNext( homeContents );
                    subscriber.onCompleted();
                }
            }
        });
    }
}
