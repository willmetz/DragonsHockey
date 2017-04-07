package com.slapshotapps.dragonshockey.activities.careerStats;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.databinding.ActivityCareerStatsBinding;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.SeasonStats;
import com.slapshotapps.dragonshockey.observables.CareerStatsObserver;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CareerStatsActivity extends AppCompatActivity {

    CareerStatsVM careerStatsVM;
    ActivityCareerStatsBinding binding;

    private FirebaseDatabase firebaseDatabase;
    private Subscription careerStatsSubscription;
    private CareerStatsAdapter careerStatsAdapter;

    private Player player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!Config.isRelease) {
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setTitle("CERT CareerStats CERT");
//        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        } catch (DatabaseException exception) {
            Timber.e("Unable to set persistance for Firebase");
        }

        //dummy data
        player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 1;
        player.number = 99;


        binding = DataBindingUtil.setContentView(this, R.layout.activity_career_stats);


        //dummy data
        ArrayList<PlayerSeasonStatsVM> dummyStats = new ArrayList<>();
        PlayerSeasonStatsVM data = new PlayerSeasonStatsVM("Fall '16");
        data.assists = 2;
        data.gamesPlayed = 9;
        data.goals = 8;
        dummyStats.add(data);
        data = new PlayerSeasonStatsVM("Fall '16");
        data.assists = 9;
        data.gamesPlayed = 9;
        data.goals = 5;
        dummyStats.add(data);
        data = new PlayerSeasonStatsVM("Current");
        data.assists = 0;
        data.gamesPlayed = 3;
        data.goals = 1;
        dummyStats.add(data);


        careerStatsAdapter = new CareerStatsAdapter();
        binding.careerStats.setAdapter(careerStatsAdapter);
        binding.careerStats.setLayoutManager(new LinearLayoutManager(this));
        binding.careerStats.addItemDecoration(new RecyclerViewDivider(this, R.drawable.schedule_divider));
        binding.careerStats.addItemDecoration(new StaticHeaderDecoration(careerStatsAdapter, binding.careerStats));
    }


    @Override
    protected void onResume() {
        super.onResume();

        careerStatsSubscription = CareerStatsObserver.getHistoricalStats(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SeasonStats>>() {
                    @Override
                    public void call(List<SeasonStats> seasonStats) {

                        careerStatsVM = new CareerStatsVM(player, null, seasonStats);
                        binding.setStats(careerStatsVM);
                        careerStatsAdapter.updateStats(careerStatsVM.getStats());
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (careerStatsSubscription != null) {
            careerStatsSubscription.unsubscribe();
            careerStatsSubscription = null;
        }
    }
}
