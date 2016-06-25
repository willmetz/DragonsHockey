package com.slapshotapps.dragonshockey.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.activities.schedule.ScheduleActivity;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

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
    TextView lastGameScore;

//    @BindView(R.id.next_game_date)
    TextView nextGameDate;

    TextView lastGameHeader;

    Subscription hockeyScheduleSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //butterknife injection doesn't appear to be working in a constraint layout at this time...
        nextGameDate = (TextView)findViewById(R.id.next_game_date );
        lastGameScore = (TextView)findViewById(R.id.last_game_score);
        lastGameHeader = (TextView)findViewById(R.id.last_game_header);

        //would be nice if butterknife worked...
        Button viewSchedule = (Button)findViewById(R.id.schedule_button);

        viewSchedule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomeActivity.this, ScheduleActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(db)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<SeasonSchedule, Observable<HomeContents>>()
                {
                    @Override
                    public Observable<HomeContents> call(SeasonSchedule games) {
                        return ScheduleObserver.getHomeScreen(db,games, new Date());
                    }
                })
                .subscribe(new Action1<HomeContents>()
                {
                    @Override
                    public void call(HomeContents homeContents)
                    {
                        setLastGameScore(homeContents.lastGame);
                        setNextGameDate(homeContents.nextGame);
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable) {
                        setLastGameScore(null);
                        setNextGameDate(null);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if( hockeyScheduleSubscription != null ){
            hockeyScheduleSubscription.unsubscribe();
            hockeyScheduleSubscription = null;
        }
    }

    protected void setLastGameScore(Game lastGame)
    {
        if( lastGame != null && lastGame.gameResult != null) {

            String winOrLoss = lastGame.gameResult.dragonsScore > lastGame.gameResult.opponentScore ? "W" : "L";
            String gameScore = String.format(getString(R.string.last_game_score), lastGame.gameResult.dragonsScore,
                    lastGame.opponent,
                    lastGame.gameResult.opponentScore,
                    winOrLoss);
            lastGameScore.setText(gameScore);
        }else{
            lastGameScore.setVisibility(View.INVISIBLE);
            lastGameHeader.setVisibility(View.INVISIBLE);
        }
    }

    protected void setNextGameDate(Game nextGame)
    {
        if( nextGame == null ){
            nextGameDate.setText("Wait till next season");
            return;
        }

        Date date = nextGame.gameTimeToDate();

        if(date != null){

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String gametime = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " +
                    FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH)) +
                    " " + DateFormaters.getGameTime(date);

            nextGameDate.setText(gametime);
        }
    }


}
