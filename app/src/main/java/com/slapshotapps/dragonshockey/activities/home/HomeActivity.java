package com.slapshotapps.dragonshockey.activities.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils;
import com.slapshotapps.dragonshockey.databinding.ActivityHomeBinding;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;
import io.fabric.sdk.android.Fabric;
import java.util.Date;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeScreenListener {

    private Subscription hockeyScheduleSubscription;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAnalytics firebaseAnalytics;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setListener(this);
        setSupportActionBar(binding.toolbar);

        if (Config.isRelease) {
            Fabric.with(this, new Crashlytics());
        } else {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("CERT Dragons Hockey CERT");
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(new Func1<SeasonSchedule, Observable<HomeContents>>() {
                @Override
                public Observable<HomeContents> call(SeasonSchedule games) {
                    return HomeScreenObserver.getHomeScreen(firebaseDatabase, games, new Date());
                }
            })
            .subscribe(new Action1<HomeContents>() {
                @Override
                public void call(HomeContents homeContents) {
                    binding.setItem(new HomeScreenViewModel(homeContents));
                    ProgressBarUtils.hideProgressBar(binding.toolbarProgressBar);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    binding.setItem(new HomeScreenViewModel(null));
                    ProgressBarUtils.hideProgressBar(binding.toolbarProgressBar);
                    Toast.makeText(HomeActivity.this, R.string.error_loading, Toast.LENGTH_LONG)
                        .show();
                }
            });

        ProgressBarUtils.displayProgressBar(binding.toolbarProgressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_admin:
                startActivity(DragonsHockeyIntents.createAdminAuthIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void onViewSchedule() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Schedule");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "900");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        startActivity(DragonsHockeyIntents.createScheduleIntent(this));
    }

    @Override
    public void onViewStats() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Stats");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "902");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        startActivity(DragonsHockeyIntents.createStatsIntent(this));
    }

    @Override
    public void onViewRoster() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Roster");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "901");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        startActivity(DragonsHockeyIntents.createRosterIntent(this));
    }
}
