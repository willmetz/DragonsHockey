package com.slapshotapps.dragonshockey.activities.admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.activities.admin.adapter.AdminEditsStatsAdapter;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.PlayerStatsViewModel;
import com.slapshotapps.dragonshockey.observables.AdminObserver;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class EditStatsAuthActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Subscription subscription;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<PlayerStatsViewModel> playerStatsViewModel;
    private AdminEditsStatsAdapter adminEditsStatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stats_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.admin_stats_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(adminEditsStatsAdapter == null) {
            subscription = AdminObserver.getPlayerStatsForGame(firebaseDatabase, getIntent().getIntExtra(DragonsHockeyIntents.EXTRA_GAME_ID, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ArrayList<PlayerStatsViewModel>>() {
                        @Override
                        public void call(ArrayList<PlayerStatsViewModel> playerStatsViewModels) {
                            playerStatsViewModel = playerStatsViewModels;

                            adminEditsStatsAdapter = new AdminEditsStatsAdapter(playerStatsViewModels);
                            recyclerView.setAdapter(adminEditsStatsAdapter);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            //TODO
                            Timber.d("Error");
                        }
                    });
        }

    }
}
