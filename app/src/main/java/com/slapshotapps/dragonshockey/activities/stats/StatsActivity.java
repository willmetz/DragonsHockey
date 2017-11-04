package com.slapshotapps.dragonshockey.activities.stats;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils;
import com.slapshotapps.dragonshockey.activities.stats.adapters.PlayerStatsVM;
import com.slapshotapps.dragonshockey.activities.stats.adapters.StatsAdapter;
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection;
import com.slapshotapps.dragonshockey.dialogs.StatsSortDialogFragment;
import com.slapshotapps.dragonshockey.managers.UserPrefsManager;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.observables.RosterObserver;
import com.slapshotapps.dragonshockey.observables.StatsObserver;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class StatsActivity extends AppCompatActivity implements PlayerStatsVM.PlayerStatsVMListener,
    StatsSortDialogFragment.StatsSortSelectionListener {

    private RecyclerView recyclerView;
    private TextView errorLoading;

    private Subscription statsSubscription;

    private FirebaseDatabase firebaseDatabase;

    private StatsSortDialogFragment statsSortDialogFragment;

    private StatsAdapter adapter;

    private UserPrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (!Config.isRelease && actionBar != null) {
            actionBar.setTitle("CERT Stats CERT");
        }

        prefsManager = new UserPrefsManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.stats_recycler_view);
        errorLoading = ButterKnife.findById(this, R.id.unable_to_load);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }
        catch (DatabaseException exception) {
            Timber.e("Unable to set persistance for Firebase");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        statsSubscription = RosterObserver.GetRoster(firebaseDatabase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(new Func1<List<Player>, rx.Observable<List<PlayerStats>>>() {
                @Override
                public rx.Observable<List<PlayerStats>> call(List<Player> players) {
                    return StatsObserver.getPlayerStats(firebaseDatabase, players);
                }
            })
            .subscribe(this::showStats, throwable -> {
                errorLoading.animate().alpha(1);
                ProgressBarUtils.hideProgressBar(findViewById(R.id.progress_bar));
            });

        ProgressBarUtils.displayProgressBar(findViewById(R.id.progress_bar));
    }

    private void showStats(List<PlayerStats> playerStats) {
        errorLoading.setAlpha(0);
        ProgressBarUtils.hideProgressBar(findViewById(R.id.progress_bar));
        adapter = new StatsAdapter(playerStats, StatsActivity.this);
        adapter.updateSortOrder(prefsManager.getStatSortPreference());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (statsSubscription != null) {
            statsSubscription.unsubscribe();
            statsSubscription = null;
        }

        if (statsSortDialogFragment != null) {
            statsSortDialogFragment.dismiss();
            statsSortDialogFragment = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sort) {
            showSortOptionsDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewPLayerStats(PlayerStats playerStats) {
        startActivity(DragonsHockeyIntents.createCareerStatsIntent(this, playerStats));
    }

    private void showSortOptionsDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        statsSortDialogFragment =
            StatsSortDialogFragment.newInstance(prefsManager.getStatSortPreference());
        statsSortDialogFragment.setListener(this);

        statsSortDialogFragment.show(fragmentManager, "tag");
    }

    @Override
    public void onSortOptionSelected(StatSortSelection sortSelection) {
        Log.d("Sort Option", String.format("Received sort option back: %s", sortSelection.name()));

        prefsManager.saveStatSortPreference(sortSelection);

        if (adapter != null) {
            adapter.updateSortOrder(sortSelection);
        }
    }
}
