package com.slapshotapps.dragonshockey.activities.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.activities.schedule.adapters.ScheduleAdapter;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * An activity to display the season schedule
 */
public class ScheduleActivity extends AppCompatActivity
{

    Subscription hockeyScheduleSubscription;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView)findViewById(R.id.schedule_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ScheduleActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule( FirebaseDatabase.getInstance() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SeasonSchedule>()
                {
                    @Override
                    public void call(SeasonSchedule schedule) {
                        recyclerView.setAdapter(new ScheduleAdapter(schedule));
                    }
                });
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if( hockeyScheduleSubscription != null ){
            hockeyScheduleSubscription.unsubscribe();
            hockeyScheduleSubscription = null;
        }
    }
}
