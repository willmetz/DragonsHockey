package com.slapshotapps.dragonshockey.activities.careerStats;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.databinding.ActivityCareerStatsBinding;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.observables.CareerStatsObserver;

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

    private PlayerStats playerStats;

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

        playerStats = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_PLAYER_STATS);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_career_stats);

        careerStatsAdapter = new CareerStatsAdapter();
        binding.careerStats.setAdapter(careerStatsAdapter);
        binding.careerStats.setLayoutManager(new LinearLayoutManager(this));
        binding.careerStats.addItemDecoration(new RecyclerViewDivider(this, R.drawable.schedule_divider));
        binding.careerStats.addItemDecoration(new StaticHeaderDecoration(careerStatsAdapter, binding.careerStats));
    }


    @Override
    protected void onResume() {
        super.onResume();

        careerStatsSubscription = CareerStatsObserver.getCareerStatsData(firebaseDatabase, playerStats.playerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CareerStatsData>() {
                    @Override
                    public void call(CareerStatsData careerStatsData) {
                        careerStatsVM = new CareerStatsVM(careerStatsData.player, null, careerStatsData.seasonStats);
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
