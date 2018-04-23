package com.slapshotapps.dragonshockey.activities.admin;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.RosterUtils;
import com.slapshotapps.dragonshockey.activities.admin.adapter.AdminEditsStatsAdapter;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminStatsViewModel;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerGameStats;
import com.slapshotapps.dragonshockey.observables.AdminObserver;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditStatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Subscription subscription;
    private FirebaseDatabase firebaseDatabase;
    private PlayerGameStats playerGameStats;
    private AdminEditsStatsAdapter adminEditsStatsAdapter;
    private int gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stats_auth);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.admin_stats_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();

        gameID = getIntent().getIntExtra(DragonsHockeyIntents.EXTRA_GAME_ID, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adminEditsStatsAdapter == null) {
            subscription = AdminObserver.getPlayerStatsForGame(firebaseDatabase, gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stats -> {
                    playerGameStats = stats;

                    adminEditsStatsAdapter = new AdminEditsStatsAdapter(getViewModel());
                    recyclerView.setAdapter(adminEditsStatsAdapter);
                }, throwable -> new AlertDialog.Builder(EditStatsActivity.this).setMessage(
                    "Unable to retrieve stats information")
                    .setPositiveButton("Ok", (dialogInterface, i) -> finish())
                    .show());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (adminEditsStatsAdapter.statsChanged()) {

            ArrayList<AdminStatsViewModel> viewModel = adminEditsStatsAdapter.getStats();

            if (playerGameStats.isKeyValid()) {
                firebaseDatabase.getReference()
                    .child(Config.GAME_STATS)
                    .child(playerGameStats.playerStatsKey)
                    .setValue(getGameStats(viewModel));
            } else {
                DatabaseReference newGameResultRef =
                    firebaseDatabase.getReference().child(Config.GAME_STATS).push();
                newGameResultRef.setValue(getGameStats(viewModel));
            }
        }
    }

    private ArrayList<AdminStatsViewModel> getViewModel() {
        ArrayList<AdminStatsViewModel> statsViewModel = new ArrayList<AdminStatsViewModel>();

        for (Player player : playerGameStats.players) {

            GameStats.Stats statsForPlayer =
                playerGameStats.playerGameStats.getPlayerStats(player.playerID);

            if (statsForPlayer == null) {
                statsForPlayer = new GameStats.Stats();
            }

            AdminStatsViewModel viewModel =
                new AdminStatsViewModel.AdminStatsVMBuilder().playerName(
                    RosterUtils.getFullName(player))
                    .playerID(player.playerID)
                    .playerNumber(player.number)
                    .goals(statsForPlayer.goals)
                    .assists(statsForPlayer.assists)
                    .present(statsForPlayer.present)
                    .penaltyMinutes(statsForPlayer.penaltyMinutes)
                    .position(player.getPosition())
                    .goalsAgainst(statsForPlayer.goalsAgainst)
                    .build();

            statsViewModel.add(viewModel);
        }

        return statsViewModel;
    }

    private GameStats getGameStats(List<AdminStatsViewModel> adminStatsViewModelList) {
        GameStats gameStats = new GameStats();

        gameStats.gameID = gameID;
        gameStats.gameStats = new ArrayList<>();

        for (AdminStatsViewModel adminStatsViewModel : adminStatsViewModelList) {
            GameStats.Stats stats = new GameStats.Stats();
            stats.playerID = adminStatsViewModel.getPlayerID();
            stats.assists = Integer.valueOf(adminStatsViewModel.getAssists());
            stats.goals = Integer.valueOf(adminStatsViewModel.getGoals());
            stats.present = adminStatsViewModel.getPresence();
            stats.penaltyMinutes = Integer.valueOf(adminStatsViewModel.getPenaltyMinutes());
            stats.goalsAgainst = Integer.valueOf(adminStatsViewModel.getGoalsAgainst());

            gameStats.gameStats.add(stats);
        }

        return gameStats;
    }
}
