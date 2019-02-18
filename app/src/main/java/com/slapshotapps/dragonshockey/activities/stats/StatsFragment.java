package com.slapshotapps.dragonshockey.activities.stats;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils;
import com.slapshotapps.dragonshockey.activities.ActionBarListener;
import com.slapshotapps.dragonshockey.activities.HockeyFragment;
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

public class StatsFragment extends HockeyFragment implements PlayerStatsVM.PlayerStatsVMListener,
    StatsSortDialogFragment.StatsSortSelectionListener {

    private RecyclerView recyclerView;
    private TextView errorLoading;

    private Subscription statsSubscription;

    private FirebaseDatabase firebaseDatabase;

    private StatsSortDialogFragment statsSortDialogFragment;

    private StatsAdapter adapter;

    private UserPrefsManager prefsManager;

    private ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }
        catch (DatabaseException exception) {
            Timber.e("Unable to set persistance for Firebase");
        }

        ActionBarListener listener = getActionBarListener();
        if(listener != null){
            listener.setTitle(getString(R.string.stats_title));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_stats, container, false);

        prefsManager = new UserPrefsManager(getContext());

        recyclerView = view.findViewById(R.id.stats_recycler_view);
        errorLoading = view.findViewById(R.id.unable_to_load);
        progressBar = view.findViewById(R.id.progress_bar);

        LinearLayoutManager manager =
            new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
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
                ProgressBarUtils.hideProgressBar(progressBar);
            });

        ProgressBarUtils.displayProgressBar(progressBar);
    }

    private void showStats(List<PlayerStats> playerStats) {
        errorLoading.setAlpha(0);
        ProgressBarUtils.hideProgressBar(progressBar);
        adapter = new StatsAdapter(playerStats, StatsFragment.this);
        adapter.updateSortOrder(prefsManager.getStatSortPreference());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_stats, menu);
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
        startActivity(DragonsHockeyIntents.createCareerStatsIntent(getContext(), playerStats));
    }

    private void showSortOptionsDialog() {
        FragmentManager fragmentManager = getFragmentManager();
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
