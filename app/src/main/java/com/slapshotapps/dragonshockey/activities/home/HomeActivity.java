package com.slapshotapps.dragonshockey.activities.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils;
import com.slapshotapps.dragonshockey.Utils.SharedPrefsUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.SeasonRecord;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import io.fabric.sdk.android.Fabric;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    private TextView lastGameScore;

    private TextView nextGameDate;

    private TextView lastGameHeader, nextGameHeader;

    private Subscription hockeyScheduleSubscription;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Config.isRelease) {
            Fabric.with(this, new Crashlytics());
        } else {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("CERT Dragons Hockey CERT");
        }

        //butterknife injection doesn't appear to be working in a constraint layout at this time...
        nextGameDate = (TextView) findViewById(R.id.next_game_date);
        lastGameScore = (TextView) findViewById(R.id.last_game_score);
        lastGameHeader = (TextView) findViewById(R.id.last_game_header);
        nextGameHeader = (TextView) findViewById(R.id.next_game_header);

        //would be nice if butterknife worked...
        Button viewSchedule = (Button) findViewById(R.id.schedule_button);
        Button viewRoster = (Button) findViewById(R.id.roster_button);
        Button viewStats = (Button) findViewById(R.id.stats_button);

        viewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Schedule");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "900");
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                startActivity(DragonsHockeyIntents.createScheduleIntent(HomeActivity.this));
            }
        });

        viewRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Roster");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "901");
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                startActivity(DragonsHockeyIntents.createRosterIntent(HomeActivity.this));
            }
        });

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Stats");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "902");
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                startActivity(DragonsHockeyIntents.createStatsIntent(HomeActivity.this));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        } catch (DatabaseException exception) {
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
                        setLastGameScore(homeContents.lastGame);
                        setNextGameDate(homeContents.nextGame);
                        setSeasonRecord(homeContents.seasonRecord);
                        ProgressBarUtils.hideProgressBar(findViewById(R.id.progress_bar_container));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setLastGameScore(null);
                        setNextGameDate(null);
                        ProgressBarUtils.hideProgressBar(findViewById(R.id.progress_bar_container));
                        Toast.makeText(HomeActivity.this,
                                R.string.error_loading,
                                Toast.LENGTH_LONG).show();
                    }
                });

        ProgressBarUtils.displayProgressBar(findViewById(R.id.progress_bar_container));
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
                //startActivity(DragonsHockeyIntents.createAdminIntent(this));
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


    protected void setLastGameScore(Game lastGame) {
        if (lastGame != null && lastGame.gameResult != null) {

            String gameResultString = FormattingUtils.getGameResultAsString(lastGame.gameResult);
            String gameScore =
                    String.format(getString(R.string.last_game_score), lastGame.gameResult.dragonsScore,
                            lastGame.opponent, lastGame.gameResult.opponentScore, gameResultString);
            lastGameScore.setText(gameScore);
            lastGameScore.animate().alpha(1.0f);
            lastGameHeader.animate().alpha(1.0f);
        } else if (lastGame != null) {
            lastGameScore.setText(R.string.update_pending);
            lastGameHeader.animate().alpha(1.0f);
            lastGameScore.animate().alpha(1.0f);
        }
    }

    protected void setNextGameDate(Game nextGame) {
        if (nextGame == null) {
            nextGameDate.setText(R.string.no_more_games);
            nextGameHeader.animate().alpha(1.0f);
            nextGameDate.animate().alpha(1.0f);
            return;
        }

        Date date = nextGame.gameTimeToDate();

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (DateUtils.isToday(date.getTime())) {

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.bubbles);

                nextGameDate.setText(getString(R.string.today_gameday, DateFormaters.getGameTime(date)));

                nextGameDate.startAnimation(animation);

            } else {
                nextGameDate.setText(DateFormaters.getGameDateTime(date));
            }
        }

        nextGameHeader.animate().alpha(1.0f);
        nextGameDate.animate().alpha(1.0f);
    }

    protected void setSeasonRecord(SeasonRecord record) {

        TextView winValue = (TextView) findViewById(R.id.record_win);
        TextView lossValue = (TextView) findViewById(R.id.record_loss);
        TextView otlValue = (TextView) findViewById(R.id.record_otl);
        TextView tieValue = (TextView) findViewById(R.id.record_tie);


        if (record == null) {
            winValue.animate().alpha(0f);
            lossValue.animate().alpha(0f);
            tieValue.animate().alpha(0f);
            otlValue.animate().alpha(0f);

            winValue.setText("");
            lossValue.setText("");
            tieValue.setText("");
            otlValue.setText("");
        } else {
            winValue.setText(String.valueOf(record.wins));
            lossValue.setText(String.valueOf(record.losses));
            tieValue.setText(String.valueOf(record.ties));
            otlValue.setText(String.valueOf(record.overtimeLosses));

            winValue.animate().alpha(1f);
            lossValue.animate().alpha(1f);
            tieValue.animate().alpha(1f);
            otlValue.animate().alpha(1f);
        }

    }

}
