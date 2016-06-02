package com.slapshotapps.dragonshockey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.HomeContents;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.last_game_score)
    TextView lastGameScore;

    @BindView(R.id.next_game_date)
    TextView nextGameDate;

    Subscription data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        data = ScheduleObserver.getGamesObservable( FirebaseDatabase.getInstance() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<DataSnapshot, Observable<List<Game>>>()
                {
                    @Override
                    public Observable<List<Game>> call(DataSnapshot dataSnapshot)
                    {
                        return ScheduleObserver.getScheduleFromSnapshot(dataSnapshot);
                    }
                })
                .flatMap(new Func1<List<Game>, Observable<HomeContents>>()
                {
                    @Override
                    public Observable<HomeContents> call(List<Game> games) {
                        return ScheduleObserver.getHomeScreenContents(games);
                    }
                })
                .subscribe(new Action1<HomeContents>()
                {
                    @Override
                    public void call(HomeContents homeContents)
                    {
                        Timber.d("Yay the rx stuff worked");

                        Game lastGame = homeContents.lastGame;
                        if( lastGame != null && lastGame.gameResult != null) {
                            String score = "Dragons " + lastGame.gameResult.dragonsScore + " "  + lastGame.opponent + " " + lastGame.gameResult.opponentScore;
                            lastGameScore.setText(score);
                        }

                        Game nextGame = homeContents.nextGame;
                        if(nextGame != null){
                            nextGameDate.setText( nextGame.gameTime );
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if( data != null ){
            data.unsubscribe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
