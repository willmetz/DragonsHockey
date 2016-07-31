package com.slapshotapps.dragonshockey.activities.roster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.observables.RosterObserver;

import java.util.List;

import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_roster);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

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

        rosterSubscription = RosterObserver.GetRoster(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("Unable to get the roster...");
                    }
                })
                .subscribe(new Action1<List<Player>>() {
                    @Override
                    public void call(List<Player> players) {
                        Timber.d("yay, got my info");
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(rosterSubscription!=null){
            rosterSubscription.unsubscribe();
            rosterSubscription = null;
        }
    }
}
