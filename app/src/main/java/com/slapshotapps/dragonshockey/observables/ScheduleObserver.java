package com.slapshotapps.dragonshockey.observables;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.dataobjects.Game;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
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

                ref.addListenerForSingleValueEvent(new ValueEventListener()
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
}
