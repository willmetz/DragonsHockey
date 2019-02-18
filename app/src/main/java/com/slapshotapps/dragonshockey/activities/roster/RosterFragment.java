package com.slapshotapps.dragonshockey.activities.roster;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.activities.ActionBarListener;
import com.slapshotapps.dragonshockey.activities.HockeyFragment;
import com.slapshotapps.dragonshockey.activities.roster.adapters.RosterAdapter;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.observables.RosterObserver;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by willmetz on 7/25/16.
 */
public class RosterFragment extends HockeyFragment {

    private FirebaseDatabase firebaseDatabase;
    private Subscription rosterSubscription;
    private RecyclerView recyclerView;
    private TextView rosterUnavailable;

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
            listener.setTitle(getString(R.string.roster_title));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_roster, container, false);

        rosterUnavailable = view.findViewById(R.id.roster_unavailable);

        recyclerView = view.findViewById(R.id.roster_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
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
                        new RosterAdapter(RosterFragment.this.getContext(), players, recyclerView);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(
                        new StaticHeaderDecoration(adapter, recyclerView));
                }
            });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (rosterSubscription != null) {
            rosterSubscription.unsubscribe();
            rosterSubscription = null;
        }
    }
}
