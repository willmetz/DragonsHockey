package com.slapshotapps.dragonshockey.activities.admin;

import android.os.Bundle;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.activities.admin.adapter.AdminScheduleAdapter;
import com.slapshotapps.dragonshockey.activities.admin.listeners.AdminClickListener;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class AdminActivity extends AppCompatActivity implements AdminClickListener {

    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private Subscription hockeyScheduleSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!Config.isRelease) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("CERT Admin CERT");
        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        } catch (DatabaseException exception) {
            Timber.e("Unable to set persistance for Firebase");
        }

        recyclerView = (RecyclerView) findViewById(R.id.schedule_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        firebaseDatabase = FirebaseDatabase.getInstance();
        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<SeasonSchedule, Observable<SeasonSchedule>>() {
                    @Override
                    public Observable<SeasonSchedule> call(SeasonSchedule schedule) {
                        return ScheduleObserver.getScheduleWithResults(firebaseDatabase, schedule);
                    }
                })
                .subscribe(new Action1<SeasonSchedule>() {
                    @Override
                    public void call(SeasonSchedule schedule) {
                        recyclerView.setAdapter(new AdminScheduleAdapter(schedule, AdminActivity.this));
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (hockeyScheduleSubscription != null) {
            hockeyScheduleSubscription.unsubscribe();
            hockeyScheduleSubscription = null;
        }
    }

    @Override
    public void onGameClick(Game game) {
        startActivity(DragonsHockeyIntents.createAdminGameIntent(this, game));
    }
}
