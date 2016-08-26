package com.slapshotapps.dragonshockey.activities.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.activities.schedule.ScheduleActivity;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.SeasonRecord;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.observables.HomeScreenObserver;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import org.w3c.dom.Text;

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

    //    @BindView(R.id.last_game_score)
    private TextView lastGameScore;

    //    @BindView(R.id.next_game_date)
    private TextView nextGameDate;

    private TextView lastGameHeader, nextGameHeader;

    private Subscription hockeyScheduleSubscription;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //butterknife injection doesn't appear to be working in a constraint layout at this time...
        nextGameDate = (TextView) findViewById(R.id.next_game_date);
        lastGameScore = (TextView) findViewById(R.id.last_game_score);
        lastGameHeader = (TextView) findViewById(R.id.last_game_header);
        nextGameHeader = (TextView) findViewById(R.id.next_game_header);

        //would be nice if butterknife worked...
        Button viewSchedule = (Button) findViewById(R.id.schedule_button);
        Button viewRoster = (Button) findViewById(R.id.roster_button);

        viewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));

            }
        });

        viewRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DragonsHockeyIntents.createRosterIntent(HomeActivity.this));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();

        try {
            firebaseDatabase.setPersistenceEnabled(true);
        }catch(DatabaseException exception){
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
                        hideProgressBar();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setLastGameScore(null);
                        setNextGameDate(null);
                        hideProgressBar();
                        Toast.makeText(HomeActivity.this,
                                R.string.error_loading,
                                Toast.LENGTH_LONG).show();
                    }
                });

        displayProgressBar();
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

            if(DateUtils.isToday(date.getTime())){

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.bubbles);

                nextGameDate.setText( getString(R.string.today_gameday, DateFormaters.getGameTime(date)));

                nextGameDate.startAnimation(animation);

            } else {


                String gametime = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " +
                        FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH)) +
                        " " + DateFormaters.getGameTime(date);

                nextGameDate.setText(gametime);
            }
        }

        nextGameHeader.animate().alpha(1.0f);
        nextGameDate.animate().alpha(1.0f);
    }

    protected void setSeasonRecord(SeasonRecord record){

        TextView winValue = (TextView)findViewById(R.id.record_win);
        TextView lossValue = (TextView)findViewById(R.id.record_loss);
        TextView otlValue = (TextView)findViewById(R.id.record_otl);
        TextView tieValue = (TextView)findViewById(R.id.record_tie);


        if(record==null){
            winValue.animate().alpha(0f);
            lossValue.animate().alpha(0f);
            tieValue.animate().alpha(0f);
            otlValue.animate().alpha(0f);

            winValue.setText("");
            lossValue.setText("");
            tieValue.setText("");
            otlValue.setText("");
        }else{
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

    protected void displayProgressBar(){

        View progressBarContainer = findViewById(R.id.progress_bar_container);
        progressBarContainer.animate().alpha(1f).setDuration(500).setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float v) {
                if(v < 0.75){
                    return 0;
                }else{
                    return v;
                }

            }
        });

    }

    protected void hideProgressBar(){
        View progressBarContainer = findViewById(R.id.progress_bar_container);
        progressBarContainer.clearAnimation();
        progressBarContainer.animate().alpha(0f);
    }

}
