package com.slapshotapps.dragonshockey.activities.stats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.activities.stats.adapters.StatsAdapter;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.observables.RosterObserver;
import com.slapshotapps.dragonshockey.observables.StatsObserver;

import java.util.List;
import java.util.Observable;

import rx.Subscription;
import rx.android.MainThreadSubscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class StatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private Subscription statsSubscription;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.stats_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }catch(DatabaseException exception){
            Timber.e("Unable to set persistance for Firebase");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        statsSubscription = RosterObserver.GetRoster(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1< List<Player>, rx.Observable<List<PlayerStats>>>(){
                    @Override
                    public rx.Observable<List<PlayerStats>> call(List<Player> players) {
                        return StatsObserver.getPlayerStats(firebaseDatabase, players);
                    }
                })
                .subscribe(new Action1<List<PlayerStats>>() {
                    @Override
                    public void call(List<PlayerStats> playerStats) {
                        StatsAdapter adapter = new StatsAdapter(playerStats);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //TODO error handling here
                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(statsSubscription!=null){
            statsSubscription.unsubscribe();
            statsSubscription = null;
        }
    }
}
