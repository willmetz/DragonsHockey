package com.slapshotapps.dragonshockey.activities.roster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.activities.roster.adapters.RosterAdapter;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.observables.RosterObserver;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by willmetz on 7/25/16.
 */
public class RosterActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private Subscription rosterSubscription;
    private RecyclerView recyclerView;
    private TextView rosterUnavailable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_roster);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!Config.isRelease) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("CERT Roster CERT");
        }

        rosterUnavailable = ButterKnife.findById(this, R.id.roster_unavailable);

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }
        catch (DatabaseException exception) {
            Timber.e("Unable to set persistance for Firebase");
        }

        recyclerView = (RecyclerView) findViewById(R.id.roster_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        rosterSubscription = RosterObserver.GetRoster(firebaseDatabase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Timber.e("Unable to get the roster...");
                    rosterUnavailable.animate().alpha(1f);
                }
            })
            .subscribe(new Action1<List<Player>>() {
                @Override
                public void call(List<Player> players) {
                    rosterUnavailable.setAlpha(0);
                    RosterAdapter adapter =
                        new RosterAdapter(RosterActivity.this, players, recyclerView);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(
                        new StaticHeaderDecoration(adapter, recyclerView));
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (rosterSubscription != null) {
            rosterSubscription.unsubscribe();
            rosterSubscription = null;
        }
    }
}
