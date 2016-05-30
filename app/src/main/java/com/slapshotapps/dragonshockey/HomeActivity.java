package com.slapshotapps.dragonshockey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slapshotapps.dragonshockey.dataobjects.Game;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    Subscription data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        database = FirebaseDatabase.getInstance();

        data = ScheduleObserver.getGamesObservable( database )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<DataSnapshot, Observable<List<Game>>>()
                {
                    @Override
                    public Observable<List<Game>> call(DataSnapshot dataSnapshot)
                    {
                        return ScheduleObserver.getScheduleFromSnapshot(dataSnapshot);
                    }
                })
                .subscribe(new Action1<List<Game>>()
                {
                    @Override
                    public void call(List<Game> games)
                    {
                        Timber.d("Yay the rx stuff worked");
                    }
                });

//        database.getReference(Config.GAMES).addListenerForSingleValueEvent(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                Log.d("tag", "i am here");
//
//                ArrayList<Game> schedule = new ArrayList<Game>();
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    schedule.add(snapshot.getValue(Game.class));
//                }
//
//
//                Log.d("tag", "i am here");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError)
//            {
//                Log.d("tag", "i am here poop");
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
